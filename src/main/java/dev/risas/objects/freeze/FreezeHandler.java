package dev.risas.objects.freeze;

/**
 * Created by Risas
 * Project: StaffMode
 * Date: 23-11-2021 - 21:29
 * Twitter: @RisasDev
 * GitHub: https://github.com/RisasDev
 */
public class FreezeHandler {

    public static void disable() {
        for (Freeze freeze : Freeze.getFreezes().values()) {
            freeze.unFreezePlayer(false);
        }
    }
}
