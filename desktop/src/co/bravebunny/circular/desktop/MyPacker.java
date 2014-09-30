package co.bravebunny.circular.desktop;

import com.badlogic.gdx.tools.texturepacker.TexturePacker;
public class MyPacker {
    public static void main (String[] args) throws Exception {
        TexturePacker.process("../android/assets/img_src", "../android/assets/img", "pack");
    }
}