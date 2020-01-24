package whatthefont;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

public class WhatTheFont {

    public static final String EXT = ".ttf";

    public static void main(String[] args) {
        System.out.println("Welcome to What The Font. An Ultimate ttf bulk renamer.\nv1.0");
        String cwd = System.getProperty("user.dir") + "/";
        File dir = new File(cwd);
        File[] files = dir.listFiles((File pathname) -> (!pathname.isDirectory()) && pathname.canRead() && pathname.getName().toLowerCase().endsWith(EXT));

        if (files.length < 1) {
            System.out.println("No readable ttf file found.");
            return;
        }
        dir = new File(cwd + "What_The_Font_renamed");
        dir.mkdir();
        for (File file : files) {
            try {
                System.out.println("FileName: " + file.getName());
                Font font = Font.createFont(Font.TRUETYPE_FONT, file);
                System.out.println("Font Name: " + font.getFontName());
                writeFile(file, dir, font.getFontName(), EXT);
                System.out.println("");
            } catch (Exception ex) {
                System.out.print("An error occurred. Reason: ");
                System.out.println(ex.getLocalizedMessage());
            }
        }

    }

    private static void writeFile(File inputFile, File outputDir, String fileName, String fileExt) throws IOException {
        fileName = fileName.replaceAll(" ", "_");
        Path outFile = Paths.get(outputDir.getAbsolutePath(), fileName + fileExt);
        int i = 0;
        while (outFile.toFile().exists()) {
            outFile = Paths.get(outputDir.getAbsolutePath(), fileName + "-copy_" + i + fileExt);
            i++;
        }
        Files.write(outFile, Files.readAllBytes(inputFile.toPath()));
    }

}
