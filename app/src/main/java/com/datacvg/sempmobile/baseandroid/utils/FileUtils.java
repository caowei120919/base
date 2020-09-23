package com.datacvg.sempmobile.baseandroid.utils;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.text.TextUtils;

import androidx.core.content.ContextCompat;

import com.datacvg.sempmobile.baseandroid.utils.javaio.FastCharArrayWriter;
import com.datacvg.sempmobile.baseandroid.utils.javaio.JoddIoDefault;
import com.datacvg.sempmobile.baseandroid.utils.javaio.StreamUtil;
import com.datacvg.sempmobile.baseandroid.utils.javaio.StringPool;
import com.datacvg.sempmobile.baseandroid.utils.javaio.UnicodeInputStream;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLDecoder;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;


/**
 * FileName: FileUtils
 * Author: 曹伟
 * Date: 2019/9/16 15:15
 * Description:
 */

public class FileUtils {

    private static final String FOLDER_SEPARATOR = "/";

    private static final String TOP_PATH = "..";

    private static final String CURRENT_PATH = ".";

    private static final char EXTENSION_SEPARATOR = '.';


    private static String MediaSavePath = "";
    private static File MediaSaveFile = null;

    private static final String MSG_NOT_A_DIRECTORY = "Not a directory: ";
    private static final String MSG_CANT_CREATE = "Can't create: ";
    private static final String MSG_NOT_FOUND = "Not found: ";
    private static final String MSG_NOT_A_FILE = "Not a file: ";
    private static final String MSG_UNABLE_TO_DELETE = "Unable to delete: ";

    /**
     * Checks if two files points to the same file.
     */
    public static boolean equals(File file1, File file2) {
        try {
            file1 = file1.getCanonicalFile();
            file2 = file2.getCanonicalFile();
        } catch (IOException ignore) {
            return false;
        }
        return file1.equals(file2);
    }

    /**
     * Converts file URLs to file. Ignores other schemes and returns <code>null</code>.
     */
    public static File toFile(URL url) {
        String fileName = toFileName(url);
        if (fileName == null) {
            return null;
        }
        return new File(fileName);
    }

    /**
     * Converts file to URL in a correct way.
     * Returns <code>null</code> in case of error.
     */
    public static URL toURL(File file) throws MalformedURLException {
        return file.toURI().toURL();
    }

    /**
     * Converts file URLs to file name. Accepts only URLs with 'file' protocol.
     * Otherwise, for other schemes returns <code>null</code>.
     */
    public static String toFileName(URL url) {
        if ((url == null) || !(url.getProtocol().equals("file"))) {
            return null;
        }
        String filename = url.getFile().replace('/', File.separatorChar);
        try {
            return URLDecoder.decode(filename, JoddIoDefault.encoding);
        } catch (UnsupportedEncodingException e) {
            return null;
        }
    }

    /**
     * Returns a file of either a folder or a containing archive.
     */
    public static File toContainerFile(URL url) {
        String protocol = url.getProtocol();
        if (protocol.equals("file")) {
            return toFile(url);
        }

        String path = url.getPath();

        return new File(URI.create(path.substring(0, path.lastIndexOf("!/"))));
    }

    public static File uri2File(Context mContext , Uri uri) {
        File file = null;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor actualimagecursor =mContext.getContentResolver()
                .query(uri, proj, null, null, null);
        int actual_image_column_index = actualimagecursor
                .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        actualimagecursor.moveToFirst();
        String img_path = actualimagecursor
                .getString(actual_image_column_index);
        file = new File(img_path);
        return file;
    }


    /**
     * Returns <code>true</code> if file exists.
     */
    public static boolean isExistingFile(File file) {
        if (file == null) {
            return false;
        }
        return file.exists() && file.isFile();
    }

    /**
     * Returns <code>true</code> if folder exists.
     */
    public static boolean isExistingFolder(File folder) {
        if (folder == null) {
            return false;
        }
        return folder.exists() && folder.isDirectory();
    }

    /**
     * Creates all folders at once.
     * @see #mkdirs(File)
     */
    public static void mkdirs(String dirs) throws IOException {
        mkdirs(new File(dirs));
    }

    /**
     * Creates all folders at once.
     */
    public static void mkdirs(File dirs) throws IOException {
        if (dirs.exists()) {
            if (!dirs.isDirectory()) {
                throw new IOException(MSG_NOT_A_DIRECTORY + dirs);
            }
            return;
        }
        if (!dirs.mkdirs()) {
            throw new IOException(MSG_CANT_CREATE + dirs);
        }
    }

    /**
     * Creates single folder.
     * @see #mkdir(File)
     */
    public static void mkdir(String dir) throws IOException {
        mkdir(new File(dir));
    }

    /**
     * Creates single folders.
     */
    public static void mkdir(File dir) throws IOException {
        if (dir.exists()) {
            if (!dir.isDirectory()) {
                throw new IOException(MSG_NOT_A_DIRECTORY + dir);
            }
            return;
        }
        if (!dir.mkdir()) {
            throw new IOException(MSG_CANT_CREATE + dir);
        }
    }

    /**
     * @see #touch(File)
     */
    public static void touch(String file) throws IOException {
        touch(new File(file));
    }

    /**
     * Implements the Unix "touch" utility. It creates a new file
     * with size 0 or, if the file exists already, it is opened and
     * closed without modifying it, but updating the file date and time.
     */
    public static void touch(File file) throws IOException {
        if (!file.exists()) {
            StreamUtil.close(new FileOutputStream(file));
        }
        file.setLastModified(System.currentTimeMillis());
    }


    public static String getSdRootPath() {
        if (isExternalStorageMounted()) {
            return Environment.getExternalStorageDirectory().getPath() + File.separator;
        } else {
            // ignore
            return null;
        }
    }

    public static boolean isExternalStorageMounted() {
        boolean isMounted = Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
        return isMounted;
    }

    public static boolean createOrExistsFolder(File file) {
        if (file == null) {
            return false;
        }
        boolean result = false;

        if (isExistingFolder(file)) {
            return true;
        }

        if (file.mkdirs()) {
            result = true;
        } else {
            result = false;
        }
        return result;
    }

    public static boolean createOrExistsFile(File file) {
        if (file == null) {
            return false;
        }
        boolean result = false;
        if (isExistingFile(file)) {
            return true;
        }

        File parentFile = file.getParentFile();
        if (!createOrExistsFolder(parentFile)) {
            return false;
        }

        try {
            if (file.createNewFile()) {
                result = true;
            } else {
                result = false;
            }
        } catch (IOException e) {
            e.printStackTrace();
            result = false;
        }
        return result;
    }

    public static String getFileNameFromURL(String url) {
        if (url != null && !url.isEmpty()) {
            return url.substring(url.lastIndexOf('/') + 1, url.length());
        }
        return "unnamed";
    }

    public static String getFilename(String path) {
        if (path == null) {
            return null;
        }
        int separatorIndex = path.lastIndexOf(FOLDER_SEPARATOR);
        return (separatorIndex != -1 ? path.substring(separatorIndex + 1) : path);
    }

    public static String getFilenameExtension(String path) {
        if (path == null) {
            return null;
        }
        int extIndex = path.lastIndexOf(EXTENSION_SEPARATOR);
        if (extIndex == -1) {
            return null;
        }
        int folderIndex = path.lastIndexOf(FOLDER_SEPARATOR);
        if (folderIndex > extIndex) {
            return null;
        }
        return path.substring(extIndex + 1);
    }

    public static String FormetFileSize(long fileS) {
        DecimalFormat df = new DecimalFormat("#0.00");
        String fileSizeString = "";
        if (fileS < 1024) {
            fileSizeString = df.format((double) fileS) + "B";
        } else if (fileS < 1048576) {
            fileSizeString = df.format((double) fileS / 1024) + "K";
        } else if (fileS < 1073741824) {
            fileSizeString = df.format((double) fileS / 1048576) + "M";
        } else {
            fileSizeString = df.format((double) fileS / 1073741824) + "G";
        }
        return fileSizeString;
    }

    public static boolean isFileNameOk(File file) {
        return Pattern.compile("[\\w%+,/=_-]+").matcher(file.getPath()).matches();
    }

    /**
     * copy file to file
     * @see #copyFile(File, File)
     */
    public static void copyFile(String src, String dest) throws IOException {
        copyFile(new File(src), new File(dest));
    }

    /**
     * Copies a file to another file.
     */
    public static void copyFile(File src, File dest) throws IOException {
        checkFileCopy(src, dest);
        doCopyFile(src, dest);
    }

    private static void checkFileCopy(File src, File dest) throws IOException {
        if (!src.exists()) {
            throw new FileNotFoundException(MSG_NOT_FOUND + src);
        }
        if (!src.isFile()) {
            throw new IOException(MSG_NOT_A_FILE + src);
        }
        if (equals(src, dest)) {
            throw new IOException("Files '" + src + "' and '" + dest + "' are equal");
        }

        File destParent = dest.getParentFile();
        if (destParent != null && !destParent.exists()) {
            if (!destParent.mkdirs()) {
                throw new IOException(MSG_CANT_CREATE + destParent);
            }
        }
    }

    /**
     * Internal file copy when most of the pre-checking has passed.
     */
    private static void doCopyFile(File src, File dest) throws IOException {
        if (dest.exists()) {
            if (dest.isDirectory()) {
                throw new IOException("Destination '" + dest + "' is a directory");
            }
        }

        // do copy file
        FileInputStream input = new FileInputStream(src);
        try {
            FileOutputStream output = new FileOutputStream(dest);
            try {
                StreamUtil.copy(input, output);
            } finally {
                StreamUtil.close(output);
            }
        } finally {
            StreamUtil.close(input);
        }

        // done

        if (src.length() != dest.length()) {
            throw new IOException("Copy file failed of '" + src + "' to '" + dest + "' due to different sizes");
        }
        dest.setLastModified(src.lastModified());
    }

    /**
     * copy file to directory
     * @see #copyFileToDir(File, File)
     */
    public static File copyFileToDir(String src, String destDir) throws IOException {
        return copyFileToDir(new File(src), new File(destDir));
    }

    /**
     * Copies a file to folder with specified copy params and returns copied destination.
     */
    public static File copyFileToDir(File src, File destDir) throws IOException {
        if (destDir.exists() && !destDir.isDirectory()) {
            throw new IOException(MSG_NOT_A_DIRECTORY + destDir);
        }
        File dest = new File(destDir, src.getName());
        copyFile(src, dest);
        return dest;
    }

    /*
     * copy dir
     */
    public static void copyDir(String srcDir, String destDir) throws IOException {
        copyDir(new File(srcDir), new File(destDir));
    }

    /**
     * Copies directory with specified copy params.
     */
    public static void copyDir(File srcDir, File destDir) throws IOException {
        checkDirCopy(srcDir, destDir);
        doCopyDirectory(srcDir, destDir);
    }

    private static void checkDirCopy(File srcDir, File destDir) throws IOException {
        if (!srcDir.exists()) {
            throw new FileNotFoundException(MSG_NOT_FOUND + srcDir);
        }
        if (!srcDir.isDirectory()) {
            throw new IOException(MSG_NOT_A_DIRECTORY + srcDir);
        }
        if (equals(srcDir, destDir)) {
            throw new IOException("Source '" + srcDir + "' and destination '" + destDir + "' are equal");
        }
    }

    private static void doCopyDirectory(File srcDir, File destDir) throws IOException {
        if (destDir.exists()) {
            if (!destDir.isDirectory()) {
                throw new IOException(MSG_NOT_A_DIRECTORY + destDir);
            }
        } else {
            if (!destDir.mkdirs()) {
                throw new IOException(MSG_CANT_CREATE + destDir);
            }
            destDir.setLastModified(srcDir.lastModified());
        }

        File[] files = srcDir.listFiles();
        if (files == null) {
            throw new IOException("Failed to list contents of: " + srcDir);
        }

        IOException exception = null;
        for (File file : files) {
            File destFile = new File(destDir, file.getName());
            try {
                if (file.isDirectory()) {
                    doCopyDirectory(file, destFile);
                } else {
                    doCopyFile(file, destFile);
                }
            } catch (IOException ioex) {
                exception = ioex;
            }
        }

        if (exception != null) {
            throw exception;
        }
    }

    /*
     * move file
     */
    public static File moveFile(String src, String dest) throws IOException {
        return moveFile(new File(src), new File(dest));
    }

    public static File moveFile(File src, File dest) throws IOException {
        checkFileCopy(src, dest);
        doMoveFile(src, dest);
        return dest;
    }

    private static void doMoveFile(File src, File dest) throws IOException {
        if (dest.exists()) {
            if (!dest.isFile()) {
                throw new IOException(MSG_NOT_A_FILE + dest);
            }
            dest.delete();
        }

        final boolean rename = src.renameTo(dest);
        if (!rename) {
            doCopyFile(src, dest);
            src.delete();
        }
    }

    /*
     * move file to dir
     */
    public static File moveFileToDir(String src, String destDir) throws IOException {
        return moveFileToDir(new File(src), new File(destDir));
    }

    public static File moveFileToDir(File src, File destDir) throws IOException {
        if (destDir.exists() && !destDir.isDirectory()) {
            throw new IOException(MSG_NOT_A_DIRECTORY + destDir);
        }
        return moveFile(src, new File(destDir, src.getName()));
    }

    /*
     * move dir
     */
    public static File moveDir(String srcDir, String destDir) throws IOException {
        return moveDir(new File(srcDir), new File(destDir));
    }

    public static File moveDir(File srcDir, File destDir) throws IOException {
        checkDirCopy(srcDir, destDir);
        doMoveDirectory(srcDir, destDir);
        return destDir;
    }

    private static void doMoveDirectory(File src, File dest) throws IOException {
        if (dest.exists()) {
            if (!dest.isDirectory()) {
                throw new IOException(MSG_NOT_A_DIRECTORY + dest);
            }
            dest = new File(dest, dest.getName());
            dest.mkdir();
        }

        final boolean rename = src.renameTo(dest);
        if (!rename) {
            doCopyDirectory(src, dest);
            deleteDir(src);
        }
    }


    public static void deleteFile(String dest) throws IOException {
        deleteFile(new File(dest));
    }

    public static void deleteFile(File dest) throws IOException {
        if (!dest.isFile()) {
            throw new IOException(MSG_NOT_A_FILE + dest);
        }
        if (!dest.delete()) {
            throw new IOException(MSG_UNABLE_TO_DELETE + dest);
        }
    }

    public static void deleteDir(String dest) throws IOException {
        deleteDir(new File(dest));
    }

    public static void deleteDir(File dest) throws IOException {
        cleanDir(dest);
        if (!dest.delete()) {
            throw new IOException(MSG_UNABLE_TO_DELETE + dest);
        }
    }

    public static void cleanDir(String dest) throws IOException {
        cleanDir(new File(dest));
    }

    /**
     * Cleans a directory without deleting it.
     */
    public static void cleanDir(File dest) throws IOException {
        if (!dest.exists()) {
            throw new FileNotFoundException(MSG_NOT_FOUND + dest);
        }

        if (!dest.isDirectory()) {
            throw new IOException(MSG_NOT_A_DIRECTORY + dest);
        }

        File[] files = dest.listFiles();
        if (files == null) {
            throw new IOException("Failed to list contents of: " + dest);
        }

        IOException exception = null;
        for (File file : files) {
            try {
                if (file.isDirectory()) {
                    deleteDir(file);
                } else {
                    file.delete();
                }
            } catch (IOException ioex) {
                exception = ioex;
                continue;
            }
        }

        if (exception != null) {
            throw exception;
        }
    }

    public static char[] readUTFChars(String fileName) throws IOException {
        return readUTFChars(new File(fileName));
    }

    /**
     * Reads UTF file content as char array.
     * @see UnicodeInputStream
     */
    public static char[] readUTFChars(File file) throws IOException {
        if (!file.exists()) {
            throw new FileNotFoundException(MSG_NOT_FOUND + file);
        }
        if (!file.isFile()) {
            throw new IOException(MSG_NOT_A_FILE + file);
        }
        long len = file.length();
        if (len >= Integer.MAX_VALUE) {
            len = Integer.MAX_VALUE;
        }
        UnicodeInputStream in = null;
        try {
            in = new UnicodeInputStream(new FileInputStream(file), null);
            FastCharArrayWriter fastCharArrayWriter = new FastCharArrayWriter((int) len);
            String encoding = in.getDetectedEncoding();
            if (encoding == null) {
                encoding = StringPool.UTF_8;
            }
            StreamUtil.copy(in, fastCharArrayWriter, encoding);
            return fastCharArrayWriter.toCharArray();
        } finally {
            StreamUtil.close(in);
        }
    }

    public static char[] readChars(String fileName) throws IOException {
        return readChars(new File(fileName), JoddIoDefault.encoding);
    }

    public static char[] readChars(File file) throws IOException {
        return readChars(file, JoddIoDefault.encoding);
    }

    public static char[] readChars(String fileName, String encoding) throws IOException {
        return readChars(new File(fileName), encoding);
    }

    /**
     * Reads file content as char array.
     */
    public static char[] readChars(File file, String encoding) throws IOException {
        if (!file.exists()) {
            throw new FileNotFoundException(MSG_NOT_FOUND + file);
        }
        if (!file.isFile()) {
            throw new IOException(MSG_NOT_A_FILE + file);
        }
        long len = file.length();
        if (len >= Integer.MAX_VALUE) {
            len = Integer.MAX_VALUE;
        }

        InputStream in = null;
        try {
            in = new FileInputStream(file);
            if (encoding.startsWith("UTF")) {
                in = new UnicodeInputStream(in, encoding);
            }
            FastCharArrayWriter fastCharArrayWriter = new FastCharArrayWriter((int) len);
            StreamUtil.copy(in, fastCharArrayWriter, encoding);
            return fastCharArrayWriter.toCharArray();
        } finally {
            StreamUtil.close(in);
        }
    }


    public static void writeChars(File dest, char[] data) throws IOException {
        outChars(dest, data, JoddIoDefault.encoding, false);
    }

    public static void writeChars(String dest, char[] data) throws IOException {
        outChars(new File(dest), data, JoddIoDefault.encoding, false);
    }

    public static void writeChars(File dest, char[] data, String encoding) throws
            IOException {
        outChars(dest, data, encoding, false);
    }

    public static void writeChars(String dest, char[] data, String encoding) throws
            IOException {
        outChars(new File(dest), data, encoding, false);
    }

    protected static void outChars(File dest, char[] data, String encoding, boolean append) throws
            IOException {
        if (dest.exists()) {
            if (!dest.isFile()) {
                throw new IOException(MSG_NOT_A_FILE + dest);
            }
        }
        Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(dest, append), encoding));
        try {
            out.write(data);
        } finally {
            StreamUtil.close(out);
        }
    }

    public static String readUTFString(String fileName) throws IOException {
        return readUTFString(new File(fileName));
    }

    /**
     * Detects optional BOM and reads UTF string from a file.
     * If BOM is missing, UTF-8 is assumed.
     * @see UnicodeInputStream
     */
    public static String readUTFString(File file) throws IOException {
        if (!file.exists()) {
            throw new FileNotFoundException(MSG_NOT_FOUND + file);
        }
        if (!file.isFile()) {
            throw new IOException(MSG_NOT_A_FILE + file);
        }
        long len = file.length();
        if (len >= Integer.MAX_VALUE) {
            len = Integer.MAX_VALUE;
        }
        UnicodeInputStream in = null;
        try {
            in = new UnicodeInputStream(new FileInputStream(file), null);
            FastCharArrayWriter out = new FastCharArrayWriter((int) len);
            String encoding = in.getDetectedEncoding();
            if (encoding == null) {
                encoding = StringPool.UTF_8;
            }
            StreamUtil.copy(in, out, encoding);
            return out.toString();
        } finally {
            StreamUtil.close(in);
        }
    }

    /**
     * Detects optional BOM and reads UTF string from an input stream.
     * If BOM is missing, UTF-8 is assumed.
     */
    public static String readUTFString(InputStream inputStream) throws IOException {
        UnicodeInputStream in = null;
        try {
            in = new UnicodeInputStream(inputStream, null);
            FastCharArrayWriter out = new FastCharArrayWriter();
            String encoding = in.getDetectedEncoding();
            if (encoding == null) {
                encoding = StringPool.UTF_8;
            }
            StreamUtil.copy(in, out, encoding);
            return out.toString();
        } finally {
            StreamUtil.close(in);
        }
    }


    public static String readString(String source) throws IOException {
        return readString(new File(source), JoddIoDefault.encoding);
    }

    public static String readString(String source, String encoding) throws IOException {
        return readString(new File(source), encoding);
    }

    public static String readString(File source) throws IOException {
        return readString(source, JoddIoDefault.encoding);
    }

    /**
     * Reads file content as string encoded in provided encoding.
     * For UTF encoded files, detects optional BOM characters.
     */
    public static String readString(File file, String encoding) throws IOException {
        if (!file.exists()) {
            throw new FileNotFoundException(MSG_NOT_FOUND + file);
        }
        if (!file.isFile()) {
            throw new IOException(MSG_NOT_A_FILE + file);
        }
        long len = file.length();
        if (len >= Integer.MAX_VALUE) {
            len = Integer.MAX_VALUE;
        }
        InputStream in = null;
        try {
            in = new FileInputStream(file);
            if (encoding.startsWith("UTF")) {
                in = new UnicodeInputStream(in, encoding);
            }
            FastCharArrayWriter out = new FastCharArrayWriter((int) len);
            StreamUtil.copy(in, out, encoding);
            return out.toString();
        } finally {
            StreamUtil.close(in);
        }
    }


    public static void writeString(String dest, String data) throws IOException {
        outString(new File(dest), data, JoddIoDefault.encoding, false);
    }

    public static void writeString(String dest, String data, String encoding) throws
            IOException {
        outString(new File(dest), data, encoding, false);
    }

    public static void writeString(File dest, String data) throws IOException {
        outString(dest, data, JoddIoDefault.encoding, false);
    }

    public static void writeString(File dest, String data, String encoding) throws
            IOException {
        outString(dest, data, encoding, false);
    }


    public static void appendString(String dest, String data) throws IOException {
        outString(new File(dest), data, JoddIoDefault.encoding, true);
    }

    public static void appendString(String dest, String data, String encoding) throws
            IOException {
        outString(new File(dest), data, encoding, true);
    }

    public static void appendString(File dest, String data) throws IOException {
        outString(dest, data, JoddIoDefault.encoding, true);
    }

    public static void appendString(File dest, String data, String encoding) throws
            IOException {
        outString(dest, data, encoding, true);
    }

    public static String getMediaSavePath() {
        return StringUtils.isEmpty(MediaSavePath) ? MediaSavePath = getMediaSaveFile().getAbsolutePath() : MediaSavePath;
    }

    public static File getMediaSaveFile() {
        if (MediaSaveFile == null) {
            String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsolutePath() + File.separatorChar + "DIMP";
            checkAndCreateDirectory(path);
            MediaSaveFile = new File(path);
        }
        return MediaSaveFile;
    }

    public static void checkAndCreateDirectory(String path) {
        if (path == null) {
            return;
        }

        File file = new File(path);
        File parent = file.getParentFile();

        if (parent != null && !parent.exists()) {
            parent.mkdirs();
        }
    }

    protected static void outString(File dest, String data, String encoding, boolean append) throws
            IOException {
        if (dest.exists()) {
            if (!dest.isFile()) {
                throw new IOException(MSG_NOT_A_FILE + dest);
            }
        }
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(dest, append);
            out.write(data.getBytes(encoding));
        } finally {
            StreamUtil.close(out);
        }
    }

    public static void writeStream(File dest, InputStream in) throws IOException {
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(dest);
            StreamUtil.copy(in, out);
        } finally {
            StreamUtil.close(out);
        }
    }

    public static void writeStream(String dest, InputStream in) throws IOException {
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(dest);
            StreamUtil.copy(in, out);
        } finally {
            StreamUtil.close(out);
        }
    }


    public static String[] readLines(String source) throws IOException {
        return readLines(new File(source), JoddIoDefault.encoding);
    }

    public static String[] readLines(String source, String encoding) throws IOException {
        return readLines(new File(source), encoding);
    }

    public static String[] readLines(File source) throws IOException {
        return readLines(source, JoddIoDefault.encoding);
    }

    /**
     * Reads lines from source files.
     */
    public static String[] readLines(File file, String encoding) throws IOException {
        if (!file.exists()) {
            throw new FileNotFoundException(MSG_NOT_FOUND + file);
        }
        if (!file.isFile()) {
            throw new IOException(MSG_NOT_A_FILE + file);
        }
        List<String> list = new ArrayList<>();

        InputStream in = null;
        try {
            in = new FileInputStream(file);
            if (encoding.startsWith("UTF")) {
                in = new UnicodeInputStream(in, encoding);
            }
            BufferedReader br = new BufferedReader(new InputStreamReader(in, encoding));
            String strLine;
            while ((strLine = br.readLine()) != null) {
                list.add(strLine);
            }
        } finally {
            StreamUtil.close(in);
        }
        return list.toArray(new String[list.size()]);
    }

    public static byte[] readBytes(String file) throws IOException {
        return readBytes(new File(file));
    }

    public static byte[] readBytes(File file) throws IOException {
        return readBytes(file, -1);
    }

    public static byte[] readBytes(File file, int fixedLength) throws IOException {
        if (!file.exists()) {
            throw new FileNotFoundException(MSG_NOT_FOUND + file);
        }
        if (!file.isFile()) {
            throw new IOException(MSG_NOT_A_FILE + file);
        }
        long len = file.length();
        if (len >= Integer.MAX_VALUE) {
            throw new IOException("File is larger then max array size");
        }

        if (fixedLength > -1 && fixedLength < len) {
            len = fixedLength;
        }

        byte[] bytes = new byte[(int) len];
        RandomAccessFile randomAccessFile = new RandomAccessFile(file, "r");
        randomAccessFile.readFully(bytes);
        randomAccessFile.close();

        return bytes;
    }


    public static void writeBytes(String dest, byte[] data) throws IOException {
        outBytes(new File(dest), data, 0, data.length, false);
    }

    public static void writeBytes(String dest, byte[] data, int off, int len) throws
            IOException {
        outBytes(new File(dest), data, off, len, false);
    }

    public static void writeBytes(File dest, byte[] data) throws IOException {
        outBytes(dest, data, 0, data.length, false);
    }

    public static void writeBytes(File dest, byte[] data, int off, int len) throws
            IOException {
        outBytes(dest, data, off, len, false);
    }


    public static void appendBytes(String dest, byte[] data) throws IOException {
        outBytes(new File(dest), data, 0, data.length, true);
    }

    public static void appendBytes(String dest, byte[] data, int off, int len) throws
            IOException {
        outBytes(new File(dest), data, off, len, true);
    }

    public static void appendBytes(File dest, byte[] data) throws IOException {
        outBytes(dest, data, 0, data.length, true);
    }

    public static void appendBytes(File dest, byte[] data, int off, int len) throws
            IOException {
        outBytes(dest, data, off, len, true);
    }

    protected static void outBytes(File dest, byte[] data, int off, int len, boolean append) throws
            IOException {
        if (dest.exists()) {
            if (!dest.isFile()) {
                throw new IOException(MSG_NOT_A_FILE + dest);
            }
        }
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(dest, append);
            out.write(data, off, len);
        } finally {
            StreamUtil.close(out);
        }
    }

    public static boolean compare(String file1, String file2) throws IOException {
        return compare(new File(file1), new File(file2));
    }

    /**
     * Compare the contents of two files to determine if they are equal or
     * not.
     * <p>
     * This method checks to see if the two files are different lengths
     * or if they point to the same file, before resorting to byte-by-byte
     * comparison of the contents.
     * <p>
     * Code origin: Avalon
     */
    public static boolean compare(File file1, File file2) throws IOException {
        boolean file1Exists = file1.exists();
        if (file1Exists != file2.exists()) {
            return false;
        }

        if (!file1Exists) {
            return true;
        }

        if ((!file1.isFile()) || (!file2.isFile())) {
            throw new IOException("Only files can be compared");
        }

        if (file1.length() != file2.length()) {
            return false;
        }

        if (equals(file1, file2)) {
            return true;
        }

        InputStream input1 = null;
        InputStream input2 = null;
        try {
            input1 = new FileInputStream(file1);
            input2 = new FileInputStream(file2);
            return StreamUtil.compare(input1, input2);
        } finally {
            StreamUtil.close(input1);
            StreamUtil.close(input2);
        }
    }

    /*
     * smart copy
     */
    public static void copy(String src, String dest) throws IOException {
        copy(new File(src), new File(dest));
    }

    /**
     * Smart copy. If source is a directory, copy it to destination.
     * Otherwise, if destination is directory, copy source file to it.
     * Otherwise, try to copy source file to destination file.
     */
    public static void copy(File src, File dest) throws IOException {
        if (src.isDirectory()) {
            copyDir(src, dest);
            return;
        }
        if (dest.isDirectory()) {
            copyFileToDir(src, dest);
            return;
        }
        copyFile(src, dest);
    }

    /*
     * smart move
     */
    public static void move(String src, String dest) throws IOException {
        move(new File(src), new File(dest));
    }

    /**
     * Smart move. If source is a directory, move it to destination.
     * Otherwise, if destination is directory, move source file to it.
     * Otherwise, try to move source file to destination file.
     */
    public static void move(File src, File dest) throws IOException {
        if (src.isDirectory()) {
            moveDir(src, dest);
            return;
        }
        if (dest.isDirectory()) {
            moveFileToDir(src, dest);
            return;
        }
        moveFile(src, dest);
    }


    /*
     * smart delete
     */
    public static void delete(String dest) throws IOException {
        delete(new File(dest));
    }

    /**
     * Smart delete of destination file or directory.
     */
    public static void delete(File dest) throws IOException {
        if (dest.isDirectory()) {
            deleteDir(dest);
            return;
        }
        deleteFile(dest);
    }

    /**
     * Returns parent for the file. The method correctly
     * processes "." and ".." in file names. The name
     * remains relative if was relative before.
     * Returns <code>null</code> if the file has no parent.
     */
    public static File getParentFile(final File file) {
        int skipCount = 0;
        File parentFile = file;
        while (true) {
            parentFile = parentFile.getParentFile();
            if (parentFile == null) {
                return null;
            }
            if (StringPool.DOT.equals(parentFile.getName())) {
                continue;
            }
            if (StringPool.DOTDOT.equals(parentFile.getName())) {
                skipCount++;
                continue;
            }
            if (skipCount > 0) {
                skipCount--;
                continue;
            }
            return parentFile;
        }
    }


    public static File createTempDirectory() throws IOException {
        return createTempDirectory(JoddIoDefault.tempFilePrefix, null, null);
    }

    /**
     * Creates temporary directory.
     */
    public static File createTempDirectory(String prefix, String suffix) throws
            IOException {
        return createTempDirectory(prefix, suffix, null);
    }

    /**
     * Creates temporary directory.
     */
    public static File createTempDirectory(String prefix, String suffix, File tempDir) throws
            IOException {
        File file = createTempFile(prefix, suffix, tempDir);
        file.delete();
        file.mkdir();
        return file;
    }

    /**
     * Simple method that creates temp file.
     */
    public static File createTempFile() throws IOException {
        return createTempFile(JoddIoDefault.tempFilePrefix, null, null, true);
    }

    /**
     * Creates temporary file.
     * If <code>create</code> is set to <code>true</code> file will be
     * physically created on the file system. Otherwise, it will be created and then
     * deleted - trick that will make temp file exist only if they are used.
     */
    public static File createTempFile(String prefix, String suffix, File tempDir, boolean create) throws
            IOException {
        File file = createTempFile(prefix, suffix, tempDir);
        file.delete();
        if (create) {
            file.createNewFile();
        }
        return file;
    }

    /**
     * Creates temporary file. Wraps java method and repeat creation several time
     * if something fail.
     */
    public static File createTempFile(String prefix, String suffix, File dir) throws
            IOException {
        int exceptionsCount = 0;
        while (true) {
            try {
                return File.createTempFile(prefix, suffix, dir).getCanonicalFile();
            } catch (IOException ioex) {    // fixes java.io.WinNTFileSystem.createFileExclusively access denied
                if (++exceptionsCount >= 50) {
                    throw ioex;
                }
            }
        }
    }

    /**
     * Calculates digest for a file using provided algorithm.
     */
    public static byte[] digest(final File file, MessageDigest algorithm) throws
            IOException {
        algorithm.reset();
        FileInputStream fis = new FileInputStream(file);
        BufferedInputStream bis = new BufferedInputStream(fis);
        DigestInputStream dis = new DigestInputStream(bis, algorithm);

        try {
            while (dis.read() != -1) {
            }
        } finally {
            StreamUtil.close(dis);
            StreamUtil.close(bis);
            StreamUtil.close(fis);
        }

        return algorithm.digest();
    }

    /**
     * Creates MD5 digest of a file.
     */
    public static String md5(final File file) throws IOException {
        return _createDigestOfFileWithAlgorithm(file, "MD5");
    }

    /**
     * Creates SHA-1 digest of a file.
     */
    public static String sha1(final File file) throws IOException {
        return _createDigestOfFileWithAlgorithm(file, "SHA-1");
    }

    /**
     * Creates SHA-256 digest of a file.
     */
    public static String sha256(final File file) throws IOException {
        return _createDigestOfFileWithAlgorithm(file, "SHA-256");
    }

    /**
     * Creates SHA-384 digest of a file.
     */
    public static String sha384(final File file) throws IOException {
        return _createDigestOfFileWithAlgorithm(file, "SHA-384");
    }

    /**
     * Creates SHA-512 digest of a file.
     */
    public static String sha512(final File file) throws IOException {
        return _createDigestOfFileWithAlgorithm(file, "SHA-512");
    }

    private static String _createDigestOfFileWithAlgorithm(final File file, final String algorithm) throws
            IOException {
        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance(algorithm);
        } catch (NoSuchAlgorithmException ignore) {
        }

        byte[] digest = digest(file, messageDigest);

        return HexUtils.encodeHexStr(digest);
    }

    /**
     * inputStream转outputStream
     * @param is 输入流
     * @return outputStream子类
     */
    public static ByteArrayOutputStream input2OutputStream(final InputStream is) {
        if (is == null) {
            return null;
        }
        try {
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            byte[] b = new byte[4096];
            int len;
            while ((len = is.read(b, 0, 4096)) != -1) {
                os.write(b, 0, len);
            }
            return os;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            StreamUtil.close(is);
        }
    }

    /**
     * outputStream转inputStream
     * @param out 输出流
     * @return inputStream子类
     */
    public ByteArrayInputStream output2InputStream(final OutputStream out) {
        if (out == null) {
            return null;
        }
        return new ByteArrayInputStream(((ByteArrayOutputStream) out).toByteArray());
    }

    /**
     * inputStream转byteArr
     * @param is 输入流
     * @return 字节数组
     */
    public static byte[] inputStream2Bytes(final InputStream is) {
        if (is == null) {
            return null;
        }
        return input2OutputStream(is).toByteArray();
    }

    /**
     * byteArr转inputStream
     * @param bytes 字节数组
     * @return 输入流
     */
    public static InputStream bytes2InputStream(final byte[] bytes) {
        if (bytes == null || bytes.length <= 0) {
            return null;
        }
        return new ByteArrayInputStream(bytes);
    }

    /**
     * outputStream转byteArr
     * @param out 输出流
     * @return 字节数组
     */
    public static byte[] outputStream2Bytes(final OutputStream out) {
        if (out == null) {
            return null;
        }
        return ((ByteArrayOutputStream) out).toByteArray();
    }

    /**
     * outputStream转byteArr
     * @param bytes 字节数组
     * @return 字节数组
     */
    public static OutputStream bytes2OutputStream(final byte[] bytes) {
        if (bytes == null || bytes.length <= 0) {
            return null;
        }
        ByteArrayOutputStream os = null;
        try {
            os = new ByteArrayOutputStream();
            os.write(bytes);
            return os;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            StreamUtil.close(os);
        }
    }

    /**
     * inputStream转string按编码
     * @param is 输入流
     * @param charsetName 编码格式
     * @return 字符串
     */
    public static String inputStream2String(final InputStream is, final String charsetName) {
        if (is == null || StringUtils.isEmpty(charsetName)) {
            return null;
        }
        try {
            return new String(inputStream2Bytes(is), charsetName);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * string转inputStream按编码
     * @param string 字符串
     * @param charsetName 编码格式
     * @return 输入流
     */
    public static InputStream string2InputStream(final String string, final String charsetName) {
        if (string == null || StringUtils.isEmpty(charsetName)) {
            return null;
        }
        try {
            return new ByteArrayInputStream(string.getBytes(charsetName));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * outputStream转string按编码
     * @param out 输出流
     * @param charsetName 编码格式
     * @return 字符串
     */
    public static String outputStream2String(final OutputStream out, final String charsetName) {
        if (out == null || StringUtils.isEmpty(charsetName)) {
            return null;
        }
        try {
            return new String(outputStream2Bytes(out), charsetName);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * string转outputStream按编码
     * @param string 字符串
     * @param charsetName 编码格式
     * @return 输入流
     */
    public static OutputStream string2OutputStream(final String string, final String charsetName) {
        if (string == null || StringUtils.isEmpty(charsetName)) {
            return null;
        }
        try {
            return bytes2OutputStream(string.getBytes(charsetName));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }


        public static File createTmpFile(Context mContext) {

            String state = Environment.getExternalStorageState();
            if (state.equals(Environment.MEDIA_MOUNTED)) {
                File pic = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
                String fileName = UUID.randomUUID().toString();
                return new File(pic, fileName + ".jpg");
            } else {
                File cacheDir = mContext.getCacheDir();
                String fileName = UUID.randomUUID().toString();
                return new File(cacheDir, fileName + ".jpg");
            }
    }

    /**
     * Get file uri
     * @param context context
     * @param shareContentType shareContentType {@link ShareContentType}
     * @param file file
     * @return Uri
     */
    public static Uri getFileUri (Context context, @ShareContentType String shareContentType, File file){

        if (context == null) {
            PLog.e("getFileUri current activity is null.");
            return null;
        }

        if (file == null || !file.exists()) {
            PLog.e("getFileUri file is null or not exists.");
            return null;
        }

        if (ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            PLog.e("getFileUri miss WRITE_EXTERNAL_STORAGE permission.");
            return null;
        }

        Uri uri = null;

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            uri = Uri.fromFile(file);
        } else {

            if (TextUtils.isEmpty(shareContentType)) {
                shareContentType = "*/*";
            }

            switch (shareContentType) {
                case ShareContentType.IMAGE :
                    uri = getImageContentUri(context, file);
                    break;
                case ShareContentType.VIDEO :
                    uri = getVideoContentUri(context, file);
                    break;
                case ShareContentType.AUDIO :
                    uri = getAudioContentUri(context, file);
                    break;
                case ShareContentType.FILE:
                    uri = getFileContentUri(context, file);
                    break;
                default: break;
            }
        }

        if (uri == null) {
            uri = forceGetFileUri(file);
        }

        return uri;
    }

    /**
     * uri convert to file real path, don't support custom FileProvider
     * @param context context
     * @param uri uri
     * @return path
     */
    public static String getFileRealPath(final Context context, final Uri uri) {

        if (context == null) {
            PLog.e("getFileRealPath current activity is null.");
            return null;
        }

        if (uri == null) {
            PLog.e("getFileRealPath uri is null.");
            return null;
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (DocumentsContract.isDocumentUri(context, uri)) {
                if (isExternalStorageDocument(uri)) {
                    final String docId = DocumentsContract.getDocumentId(uri);
                    final String[] split = docId.split(":");
                    final String type = split[0];

                    if ("primary".equalsIgnoreCase(type)) {
                        return Environment.getExternalStorageDirectory() + "/" + split[1];
                    }
                } else if (isDownloadsDocument(uri)) {
                    final String id = DocumentsContract.getDocumentId(uri);
                    final Uri contentUri = ContentUris.withAppendedId(
                            Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                    return getDataColumn(context, contentUri, null, null);
                } else if (isMediaDocument(uri)) {
                    final String docId = DocumentsContract.getDocumentId(uri);
                    final String[] split = docId.split(":");
                    final String type = split[0];

                    Uri contentUri;
                    if ("image".equals(type)) {
                        contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                    } else if ("video".equals(type)) {
                        contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                    } else if ("audio".equals(type)) {
                        contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                    } else {
                        contentUri = MediaStore.Files.getContentUri("external");
                    }

                    final String selection = "_id=?";
                    final String[] selectionArgs = new String[] { split[1] };

                    return getDataColumn(context, contentUri, selection, selectionArgs);
                }
            } else if ("file".equalsIgnoreCase(uri.getScheme())) {
                return uri.getPath();
            }
        } else {
            String filePath = null;
            if ("content".equalsIgnoreCase(uri.getScheme())) {
                Cursor cursor = context.getContentResolver().query(uri,
                        new String[]{MediaStore.Files.FileColumns.DATA}, null, null, null);
                if (cursor != null) {
                    if (cursor.moveToFirst()) {
                        filePath = cursor.getString(cursor.getColumnIndex(MediaStore.Files.FileColumns.DATA));
                    }
                    cursor.close();
                }
            } else if ("file".equalsIgnoreCase(uri.getScheme())) {
                filePath =  uri.getPath();
            }

            return filePath;
        }
        return null;
    }


    /**
     * forceGetFileUri
     * @param shareFile shareFile
     * @return Uri
     */
    private static Uri forceGetFileUri(File shareFile) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            try {
                @SuppressLint("PrivateApi")
                Method rMethod = StrictMode.class.getDeclaredMethod("disableDeathOnFileUriExposure");
                rMethod.invoke(null);
            } catch (Exception e) {
                PLog.e(e.getMessage());
            }
        }

        return Uri.parse("file://" + shareFile.getAbsolutePath());
    }

    /**
     * getFileContentUri
     * @param context context
     * @param file file
     * @return Uri
     */
    private static Uri getFileContentUri(Context context, File file) {
        String volumeName = "external";
        String filePath = file.getAbsolutePath();
        String[] projection = new String[]{MediaStore.Files.FileColumns._ID};
        Uri uri = null;

        Cursor cursor = context.getContentResolver().query(MediaStore.Files.getContentUri(volumeName), projection,
                MediaStore.Images.Media.DATA + "=? ", new String[] { filePath }, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                int id = cursor.getInt(cursor.getColumnIndex(MediaStore.Files.FileColumns._ID));
                uri = MediaStore.Files.getContentUri(volumeName, id);
            }
            cursor.close();
        }

        return uri;
    }

    /**
     * Gets the content:// URI from the given corresponding path to a file
     *
     * @param context context
     * @param imageFile imageFile
     * @return content Uri
     */
    private static Uri getImageContentUri(Context context, File imageFile) {
        String filePath = imageFile.getAbsolutePath();
        Cursor cursor = context.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                new String[] { MediaStore.Images.Media._ID }, MediaStore.Images.Media.DATA + "=? ",
                new String[] { filePath }, null);
        Uri uri = null;

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                int id = cursor.getInt(cursor.getColumnIndex(MediaStore.MediaColumns._ID));
                Uri baseUri = Uri.parse("content://media/external/images/media");
                uri = Uri.withAppendedPath(baseUri, "" + id);
            }

            cursor.close();
        }

        if (uri == null) {
            ContentValues values = new ContentValues();
            values.put(MediaStore.Images.Media.DATA, filePath);
            uri = context.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        }

        return uri;
    }

    /**
     * Gets the content:// URI from the given corresponding path to a file
     *
     * @param context context
     * @param videoFile videoFile
     * @return content Uri
     */
    private static Uri getVideoContentUri(Context context, File videoFile) {
        Uri uri = null;
        String filePath = videoFile.getAbsolutePath();
        Cursor cursor = context.getContentResolver().query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                new String[] { MediaStore.Video.Media._ID }, MediaStore.Video.Media.DATA + "=? ",
                new String[] { filePath }, null);

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                int id = cursor.getInt(cursor.getColumnIndex(MediaStore.MediaColumns._ID));
                Uri baseUri = Uri.parse("content://media/external/video/media");
                uri = Uri.withAppendedPath(baseUri, "" + id);
            }

            cursor.close();
        }

        if (uri == null) {
            ContentValues values = new ContentValues();
            values.put(MediaStore.Video.Media.DATA, filePath);
            uri = context.getContentResolver().insert(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, values);
        }

        return uri;
    }


    /**
     * Gets the content:// URI from the given corresponding path to a file
     *
     * @param context context
     * @param audioFile audioFile
     * @return content Uri
     */
    private static Uri getAudioContentUri(Context context, File audioFile) {
        Uri uri = null;
        String filePath = audioFile.getAbsolutePath();
        Cursor cursor = context.getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                new String[] { MediaStore.Audio.Media._ID }, MediaStore.Audio.Media.DATA + "=? ",
                new String[] { filePath }, null);

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                int id = cursor.getInt(cursor.getColumnIndex(MediaStore.MediaColumns._ID));
                Uri baseUri = Uri.parse("content://media/external/audio/media");
                uri = Uri.withAppendedPath(baseUri, "" + id);
            }

            cursor.close();
        }
        if (uri == null) {
            ContentValues values = new ContentValues();
            values.put(MediaStore.Audio.Media.DATA, filePath);
            uri = context.getContentResolver().insert(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, values);
        }

        return uri;
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    private static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    private static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    private static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    /**
     * Get the value of the data column for this Uri. This is useful for
     * MediaStore Uris, and other file-based ContentProviders.
     *
     * @param context
     *            The context.
     * @param uri
     *            The Uri to query.
     * @param selection
     *            (Optional) Filter used in the query.
     * @param selectionArgs
     *            (Optional) Selection arguments used in the query.
     * @return The value of the _data column, which is typically a file path.
     */
    private static String getDataColumn(Context context, Uri uri, String selection,
                                        String[] selectionArgs) {

        Cursor cursor = null;
        final String[] projection = { MediaStore.Files.FileColumns.DATA };

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                return cursor.getString(cursor.getColumnIndex(MediaStore.Files.FileColumns.DATA));
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return null;
    }
}
