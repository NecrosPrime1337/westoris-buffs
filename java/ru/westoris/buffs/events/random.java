package ru.westoris.buffs.events;

import java.util.Random;

public class random {
    private static boolean event;
    private static int val;

    public static boolean randomize(int chance) {

        if (chance >= 3) {
            Random rand = new Random();
            val = rand.nextInt(chance);

            if (val != 0) {
                event = true;
            } else {
                event = false;
            }
        } else {
            event = false;
        }
        return event;
    }

}
