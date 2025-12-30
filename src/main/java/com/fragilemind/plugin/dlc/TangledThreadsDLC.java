package com.fragilemind.plugin.dlc;

import com.fragilemind.model.Choice;
import com.fragilemind.model.DreamScene;
import com.fragilemind.model.Item;
import com.fragilemind.model.Player;
import com.fragilemind.model.RealityScene;
import com.fragilemind.model.Scene;
import com.fragilemind.plugin.IGamePlugin;

public class TangledThreadsDLC implements IGamePlugin {
    
    // DLC içindeki durumu takip etmek için private değişken
    private boolean acquiredCharredSpire = false;

    @Override
    public String getName() {
        return "DLC: TANGLED THREADS - The Veridian Incident";
    }

    @Override
    public void onEnable() {
        System.out.println(">> [SYSTEM LOG] DLC Loaded: Tangled Threads. Memory fragmentation detected.");
    }

    @Override
    public Scene getStartingScene() {
        // --- SCENE 1 (Dream) ---
        // Polymorphism: DreamScene kullanıyoruz çünkü burası bir hayal.
        Scene s1_Workshop = new DreamScene(
            "dlc_s1",
            "THE INFINITE CATHEDRAL (14 Months Ago)",
            "[INT. MODEL WORKSHOP - 3:00 AM]\n" +
            "The smell of balsa wood and ozone. You are walking through the halls of a cathedral that doesn't exist.\n" +
            "NARRATOR: Perfect symmetry. A space where gravity doesn't exist.",
            100 // Yüksek distortion level
        );

        // --- SCENE 2 (Reality/Chaos) ---
        Scene s2_Fire = new RealityScene(
            "dlc_s2",
            "THE VOICES FROM THE CRACKS",
            "[REALITY BREACH]\n" +
            "The workshop is on fire. The alarm is screaming.\n" +
            "JUDE (Behind Glass): \"Rowan?! The lock is sparking! I can't get it open! Answer me!\""
        );

        // --- SCENE 3 (Hospital) ---
        Scene s3_Hospital = new RealityScene(
            "dlc_s3",
            "THE AWAKENING",
            "[INT. HOSPITAL ROOM]\n" +
            "White ceiling. Angry parents. Ash on your hands."
        );

        // --- BAĞLANTILAR ---

        s1_Workshop.addChoice(new Choice("Continue building...", s2_Fire, null));

        // OPTION A: Wake Up (Good Conscience)
        s2_Fire.addChoice(new Choice(
            "[WAKE UP] Smash the emergency glass.", 
            s3_Hospital, 
            p -> {
                System.out.println(">> RESULT: You saved Jude. The Cathedral is destroyed.");
                p.updateStats(-10, 20); // Trauma, High Conscience
                this.acquiredCharredSpire = false;
            }
        ));

        // OPTION B: Finish Design (Low Sanity Only)
        s2_Fire.addChoice(new Choice(
            "[FINISH THE DESIGN] Ignore the screams. Glue the final spire.", 
            s3_Hospital,
            p -> p.getSanity() < 50, // Condition
            "Requires Low Sanity (<50)", 
            p -> {
                System.out.println(">> RESULT: You finished the spire. The world burned around you.");
                p.updateStats(20, -50); // Sanity Boost, Conscience Collapse
                this.acquiredCharredSpire = true; // Kötü eşyayı kazanır
            }
        ));
        
        // Hospital'dan çıkış yok (null), çünkü buradan ana oyuna döneceğiz.
        s3_Hospital.addChoice(new Choice("Return to present day...", null, null));

        return s1_Workshop;
    }

    @Override
    public void applyAftermath(Player player) {
        if (acquiredCharredSpire) {
            // Polymorphism: Item yerine SpecialItem ekliyoruz
            Item spire = new Item("item_spire", "The Charred Spire", "A blackened piece of wood. It feels warm.");
            player.addItem(spire);
            System.out.println("\n>> [ITEM ACQUIRED] You kept a piece of the tragedy.");
        }
    }
}