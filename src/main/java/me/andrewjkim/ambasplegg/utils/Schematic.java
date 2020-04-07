package me.andrewjkim.ambasplegg.utils;

public class Schematic {

    String name;
    short[] blocks;
    byte[] data;
    short width;
    short length;
    short height;

    public Schematic(String name, short[] blocks, byte[] data, short width, short length, short height) {
        this.name = name;
        this.blocks = blocks;
        this.data = data;
        this.width = width;
        this.length = length;
        this.height = height;
    }

}
