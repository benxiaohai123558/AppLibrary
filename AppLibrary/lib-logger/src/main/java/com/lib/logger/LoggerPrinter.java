package com.lib.logger;

import com.lib.logger.config.LoggerConfig;
import com.lib.logger.constants.LoggerConstants;
import com.lib.logger.inter.IPrinter;
import com.lib.logger.util.LoggerUtil;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

/**
 * author: Guazi.
 * time  : 2017/2/20.
 * desc  :
 */


public class LoggerPrinter implements IPrinter {

    private static final int DEBUG = 3;
    private static final int VERBOSE = 2;
    private static final int INFO = 4;
    private static final int WARN = 5;
    private static final int ERROR = 6;
    private static final int ASSERT = 7;

    /**
     * Android's max limit for a log entry is ~4076 bytes,
     * so 4000 bytes is used as chunk size since default charset
     * is UTF-8
     */
    private static final int CHUNK_SIZE = 4000;

    /**
     * It is used for json pretty print
     */
    private static final int JSON_INDENT = 2;

    /**
     * The minimum stack trace index, starts at this class after two native calls.
     */
    private static final int MIN_STACK_OFFSET = 3;

    /**
     * Drawing toolbox
     */
    private static final String SINGLE_DIVIDER = "────────────────────────────────────────────";

    private String tag;

    private final ThreadLocal<String> localTag = new ThreadLocal<>();

    private final ThreadLocal<Integer> localMethodCount = new ThreadLocal<>();

    private LoggerConfig loggerConfig = new LoggerConfig();

    private File logFile;

    public LoggerPrinter() {
        init(LoggerConstants.DEFAULT_TAG);
    }

    @Override
    public LoggerConfig init(String tag) {
        if (tag == null) {
            throw new NullPointerException("tag may not be null");
        }
        if (tag.trim().length() == 0) {
            throw new IllegalStateException("tag may not be empty");
        }
        this.tag = tag;
        return loggerConfig;
    }

    @Override
    public IPrinter t(String tag) {
        if (tag != null) {
            localTag.set(formatTag(this.tag, tag));
        }
        return this;
    }

    @Override
    public void i(Object... args) {
        log(INFO, null, args);
    }

    @Override
    public void v(Object... args) {
        log(VERBOSE, null, args);
    }


    @Override
    public void w(Object... args) {
        log(WARN, null, args);
    }


    @Override
    public void wtf(Object... args) {
        log(ASSERT, null, args);
    }


    @Override
    public void d(Object... args) {
        log(DEBUG, null, args);
    }


    @Override
    public void e(Object... args) {
        log(ERROR, null, args);
    }


    @Override
    public void e(Throwable throwable, Object... args) {
        log(ERROR, throwable, args);
    }

    @Override
    public void json(String json) {
        if (!loggerConfig.isLogger()) {
            return;
        }
        if (LoggerUtil.isEmpty(json)) {
            d("Empty/Null json content");
            return;
        }
        try {
            json = json.trim();
            if (json.startsWith("{")) {
                JSONObject jsonObject = new JSONObject(json);
                String message = jsonObject.toString(JSON_INDENT);
                i(message);
                return;
            }
            if (json.startsWith("[")) {
                JSONArray jsonArray = new JSONArray(json);
                String message = jsonArray.toString(JSON_INDENT);
                i(message);
                return;
            }
        } catch (Exception e) {
            this.e(e);
        }
    }

    @Override
    public void xml(String xml) {
        if (!loggerConfig.isLogger()) {
            return;
        }
        if (LoggerUtil.isEmpty(xml)) {
            d("Empty/Null xml content");
            return;
        }
        try {
            Source xmlInput = new StreamSource(new StringReader(xml));
            StreamResult xmlOutput = new StreamResult(new StringWriter());
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
            transformer.transform(xmlInput, xmlOutput);
            i(xmlOutput.getWriter().toString().replaceFirst(">", ">\n"));
        } catch (Exception e) {
            this.e(e);
        }
    }

    private void log(int priority, Throwable throwable, Object... args) {
        if (!loggerConfig.isLogger()) {
            return;
        }
        synchronized (this) {
            String tag = getTag();
            String message = getMessage(throwable, args);
            int methodCount = getMethodCount();
            logHeaderContent(priority, tag, methodCount);

            //get bytes of message with system's default charset (which is UTF-8 for Android)
            byte[] bytes = message.getBytes();
            int length = bytes.length;
            if (length <= CHUNK_SIZE) {
                logContent(priority, tag, message);
                logBottomBorder(priority, tag);
                return;
            }
            for (int i = 0; i < length; i += CHUNK_SIZE) {
                int count = Math.min(length - i, CHUNK_SIZE);
                //create a new String with system's default charset (which is UTF-8 for Android)
                logContent(priority, tag, new String(bytes, i, count));
            }
            logBottomBorder(priority, tag);
        }
    }

    private String getTag() {
        String tag = localTag.get();
        if (tag != null) {
            localTag.remove();
            return tag;
        }
        return this.tag;
    }

    private String formatTag(String tag, String newtag) {
        if (!LoggerUtil.isEmpty(newtag) && !LoggerUtil.equals(tag, newtag)) {
            return tag + "-" + newtag;
        }
        return tag;
    }

    /**
     * 获取输出字符串
     */
    private String getMessage(Throwable throwable, Object... args) {
        StringBuffer sb = new StringBuffer();
        if (throwable != null) {
            sb.append(LoggerUtil.getStackTraceString(throwable));
        }
        if (args != null && args.length != 0) {
            for (Object objects : args) {
                if (objects != null) {
                    if (!LoggerUtil.isEmpty(sb)) {
                        sb.append("\n");
                    }
                    if (objects.getClass().isArray()) {
                        sb.append(Arrays.deepToString((Object[]) objects));
                    } else {
                        sb.append(objects.toString());
                    }
                }
            }
        }
        return sb.toString();
    }

    private int getMethodCount() {
        Integer count = localMethodCount.get();
        int result = loggerConfig.getMethodCount();
        if (count != null) {
            localMethodCount.remove();
            result = count;
        }
        if (result < 0) {
            throw new IllegalStateException("methodCount cannot be negative");
        }
        return result;
    }


    private void logBottomBorder(int logType, String tag) {
        logChunk(logType, tag, SINGLE_DIVIDER);
    }

    private void logContent(int logType, String tag, String chunk) {
        String[] lines = chunk.split(System.getProperty("line.separator"));
        for (String line : lines) {
            logChunk(logType, tag, line);
        }
    }

    private void logHeaderContent(int logType, String tag, int methodCount) {
        StackTraceElement[] trace = Thread.currentThread().getStackTrace();
        if (loggerConfig.isShowThreadInfo()) {
            logChunk(logType, tag, "Thread: " + Thread.currentThread().getName());
        }
        String level = "";

        int stackOffset = getStackOffset(trace);

        //corresponding method count with the current stack may exceeds the stack trace. Trims the count
        if (methodCount + stackOffset > trace.length) {
            methodCount = trace.length - stackOffset - 1;
        }

        for (int i = methodCount; i > 0; i--) {
            int stackIndex = i + stackOffset;
            if (stackIndex >= trace.length) {
                continue;
            }
            StringBuilder builder = new StringBuilder();
            builder.append("").append(level).append(getSimpleClassName(trace[stackIndex].getClassName())).append(".").append(trace[stackIndex].getMethodName()).append(" ").append(" (").append(trace[stackIndex].getFileName()).append(":").append(trace[stackIndex].getLineNumber()).append(")");
            level += "   ";
            logChunk(logType, tag, builder.toString());
        }
    }


    /**
     * Determines the starting index of the stack trace, after method calls made by this class.
     *
     * @param trace the stack trace
     * @return the stack offset
     */
    private int getStackOffset(StackTraceElement[] trace) {
        for (int i = MIN_STACK_OFFSET; i < trace.length; i++) {
            StackTraceElement e = trace[i];
            String name = e.getClassName();
            if (!name.equals(LoggerPrinter.class.getName()) && !name.equals(Logger.class.getName())) {
                return --i;
            }
        }
        return -1;
    }

    private String getSimpleClassName(String name) {
        int lastIndex = name.lastIndexOf(".");
        return name.substring(lastIndex + 1);
    }

    private void logChunk(int logType, String tag, String chunk) {
        String finalTag = tag;
        printFile(tag, chunk);
        switch (logType) {
            case ERROR:
                loggerConfig.getLogger().e(finalTag, chunk);
                break;
            case INFO:
                loggerConfig.getLogger().i(finalTag, chunk);
                break;
            case VERBOSE:
                loggerConfig.getLogger().v(finalTag, chunk);
                break;
            case WARN:
                loggerConfig.getLogger().w(finalTag, chunk);
                break;
            case ASSERT:
                loggerConfig.getLogger().wtf(finalTag, chunk);
                break;
            case DEBUG:
                loggerConfig.getLogger().d(finalTag, chunk);
                break;
            default:
                loggerConfig.getLogger().d(finalTag, chunk);
                break;
        }
    }

    private void printFile(String tag, String logVaule) {
        if (!loggerConfig.isPrintFile()) {
            return;
        }
        if (!LoggerUtil.isSDCardEnable()) return;
        try {
            File fileRoot = LoggerUtil.createLogFile(loggerConfig.getLogFilePath());
            String name = LoggerUtil.formatAsYearToDay(new Date(System.currentTimeMillis()));
            File file = LoggerUtil.createLogFile(fileRoot, String.format("%s%s", name, LoggerConstants.DEFAULT_SUFFIX));
            LoggerUtil.writeFile(file, String.format("%s%s", tag, "\n"));
            LoggerUtil.writeFile(file, String.format("%s%s", logVaule, "\n"));
        } catch (Exception e) {
            e(e);
        }
    }

    @Deprecated
    public boolean clearLogFile() {
        try {
            List<File> files = LoggerUtil.listFilesInDir(LoggerUtil.createLogFile(loggerConfig.getLogFilePath()));
            if (files != null && !files.isEmpty()) {
                List<File> tempFile = new CopyOnWriteArrayList<>();
                Date nowData = new Date(System.currentTimeMillis());
                for (File file : files) {
                    String name = null;
                    if (file.getName().contains(".")) {
                        name = file.getName().substring(0, file.getName().indexOf("."));
                    }
                    if (name == null) return false;
                    Date d = LoggerUtil.parse(name);
                    if (LoggerUtil.getDateByDayOffset(d, loggerConfig.getDate()).compareTo(nowData) < 0) {
                        tempFile.add(file);
                    }
                }
                for (File file : tempFile) {
                    file.delete();
                }
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
