package com.epam.mjc.nio;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;


public class FileReader {

    public Profile getDataFromFile(File file) {
        Map<String, String> personalData = new HashMap<>();
        Profile profile = new Profile();
        StringBuilder content = new StringBuilder();
        Path path = Paths.get(file.getAbsolutePath());

        try (RandomAccessFile randomAccessFile = new RandomAccessFile(file, "r");
             FileChannel inChannel = randomAccessFile.getChannel()) {
            ByteBuffer buffer = ByteBuffer.allocate(1024);

            while (inChannel.read(buffer) > 0) {
                buffer.flip();
                for (int i = 0; i < buffer.limit(); i++) {
                    content.append((char) buffer.get());
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (NumberFormatException n) {
            n.printStackTrace();
        }

        String[] d = content.toString().split(System.lineSeparator());

        for (int x = 0; x < d.length; x++) {
            String row = d[x];
            String[] keyValue = row.split(":");
            personalData.put(keyValue[0].trim(), keyValue[1].trim());
        }

        try {
            profile.setName(personalData.get("Name"));
            profile.setAge(Integer.parseInt(personalData.get("Age")));
            profile.setEmail(personalData.get("Email"));
            profile.setPhone(Long.parseLong(personalData.get("Phone")));
        } catch (NumberFormatException n) {
            n.printStackTrace();
        }
        return profile;
    }
}
