package com.epam.mjc.nio;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;


public class FileReader {

    public Profile getDataFromFile(File file) {
        StringBuilder content = new StringBuilder();
        String[] profileData = new String[4];
        try (RandomAccessFile aFile = new RandomAccessFile(file, "r");
             FileChannel inChannel = aFile.getChannel()) {

            long fileSize = inChannel.size();

            ByteBuffer buffer = ByteBuffer.allocate((int) fileSize);

            inChannel.read(buffer);
            buffer.flip();

            for (int i = 0; i < buffer.limit(); i++) {
                content.append((char) buffer.get());
            }

            String[] splitContent = content.toString().split("\n");

            for (int i = 0; i < splitContent.length; i++) {
                int indexToSplit = splitContent[i].indexOf(":");
                profileData[i] = splitContent[i].substring(indexToSplit + 2).trim();
            }

        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        return new Profile(profileData[0], Integer.parseInt(profileData[1]), profileData[2], Long.parseLong(profileData[3]));
    }
}
