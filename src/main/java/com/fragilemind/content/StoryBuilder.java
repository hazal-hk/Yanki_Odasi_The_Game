package com.fragilemind.content;

import com.fragilemind.model.Choice;
import com.fragilemind.model.DreamScene;
import com.fragilemind.model.RealityScene;
import com.fragilemind.model.Scene;

public class StoryBuilder {

    public static Scene buildMainStory() {

        // =================================================================================
        // ADIM 1: SAHNELERİ OLUŞTUR (Scene Instantiation)
        // =================================================================================

        // --- ACT 1: THE CRACKS ---

        Scene s1_Laundry = new RealityScene(
            "act1_scene1",
            "ACT 1 - SCENE 1: THE ANCHOR",
            "[INT. DORM LAUNDRY ROOM - DAY]\n" +
            "The humid air smells of synthetic lavender. You are staring at a washing machine.\n" +
            "ETHAN: You’ve been staring at that cycle for ten minutes. The latch is broken."
        );

        Scene s2_Studio = new DreamScene(
            "act1_scene2",
            "SCENE 2: THE TRIGGER",
            "[INT. ARCHITECTURE STUDIO]\n" +
            "PROF. FINCH: We create walls to define where 'we' end and the 'world' begins.\n" +
            "(The blueprints on the wall begin to float... gravity is failing...)",
            80 
        );

        Scene s3_Library = new RealityScene(
            "act1_scene3",
            "SCENE 3: THE MIRROR",
            "[INT. LIBRARY STACKS - NIGHT]\n" +
            "You are holding a crushed scale model. VIVIAN appears.\n" +
            "VIVIAN: That’s a lot of structural damage for a 'peaceful' study session."
        );

        Scene s4_Crossroads = new RealityScene(
            "act2_crossroads",
            "ACT 2: THE CROSSROADS",
            "[INT. DORM ROOM]\n" +
            "On the desk: An invitation to the Obsidian Gala (Vivian).\n" +
            "Next to it: Your sketchbook for the North Wing (The Mental Palace).\n" +
            "NARRATOR: Where do you belong, Architect?"
        );

        Scene s5a_Gala = new RealityScene("path_a", "PATH A: THE OBSIDIAN GALA", "Glass walls. Cold champagne. Judging eyes.");
        Scene s5b_NorthWing = new DreamScene("path_b", "PATH B: THE NORTH WING", "Silence. Marble floors. Perfect symmetry.", 100);

        // --- ACT 2: THE ANATOMY OF A GHOST ---

        Scene s6_FinchOffice = new RealityScene(
            "act2_finch",
            "PROFESSOR FINCH'S OFFICE",
            "[INT. FINCH’S OFFICE - LATE AFTERNOON]\n" +
            "A labyrinth of geometry. Finch slides a newspaper clipping across the desk.\n" +
            "FINCH: 'Student Trapped in Workshop Blaze. Negligence Suspected.'\n" +
            "FINCH: You didn't leave because you couldn't... or because you didn't want to?"
        );

        // Telefon Sahnesi (Rüya/Halüsinasyon)
        Scene s7_PhantomCall = new DreamScene(
            "act2_call",
            "THE JUDE VARIABLE",
            "[LATER THAT NIGHT]\nThe phone rings. All you hear is crackling fire and a mechanical ventilator.",
            90 
        );


        // =================================================================================
        // ADIM 2: SEÇİMLERİ BAĞLA (Wiring & Logic)
        // =================================================================================

        // --- SAHNE 1: LAUNDRY ---
        s1_Laundry.addChoice(new Choice(
            "[KINDNESS] \"Thanks for the tip, Ethan.\"", 
            s2_Studio, 
            p -> p.getConscience() > 20, "Requires Conscience > 20",       
            p -> {
                p.log("ETHAN: No problem, Architect. Don't drown in there."); // Log arayüze basar
                p.updateStats(10, 10);
            }
        ));

        s1_Laundry.addChoice(new Choice(
            "[DISMISSIVE] (Open the door roughly).", 
            s2_Studio, 
            p -> {
                p.log("ETHAN: ...Okay, chill out.");
                p.updateStats(0, -5);
            }
        ));

        s1_Laundry.addChoice(new Choice(
            "[PARANOID] (Stare at him. Is he real?)", 
            s2_Studio, 
            p -> p.getSanity() < 40, "Requires Low Sanity (<40)", 
            p -> {
                p.log("NARRATOR: He looks too perfect. Like a drawing.");
                p.updateStats(5, -20);
            }
        ));

        // --- SAHNE 2: STUDIO ---
        s2_Studio.addChoice(new Choice("Snap back to reality -> Go to Library", s3_Library, null));

        // --- SAHNE 3: LIBRARY ---
        s3_Library.addChoice(new Choice(
            "[VULNERABLE] \"I go to places that aren't on the map...\"", 
            s4_Crossroads, 
            p -> p.getConscience() >= 30, "Requires Conscience >= 30", 
            p -> {
                p.log("VIVIAN: Maybe you should take a map next time. Or a friend.");
                p.updateStats(-10, 20);
            }
        ));

        s3_Library.addChoice(new Choice(
            "[DEFENSIVE] \"Don't make a crime out of a nap.\"", 
            s4_Crossroads, 
            p -> {
                p.log("VIVIAN: Just checking your structural integrity.");
                p.updateStats(10, -15);
            }
        ));

        // --- SAHNE 4: CROSSROADS ---
        s4_Crossroads.addChoice(new Choice("Go to the Gala (Choose Reality)", s5a_Gala, null));
        s4_Crossroads.addChoice(new Choice("Enter the North Wing (Choose Delusion)", s5b_NorthWing, p -> p.updateStats(-20, 0)));


        // --- ACT 1 SONU -> ACT 2 BAŞLANGICI ---
        // Gala veya North Wing bittikten sonra hikaye Finch'in ofisine akar.
        
        s5a_Gala.addChoice(new Choice("Next Day: Professor Finch's Office", s6_FinchOffice, null));
        s5b_NorthWing.addChoice(new Choice("Wake Up: Professor Finch's Office", s6_FinchOffice, null));


        // --- ACT 2: FINCH'S OFFICE SEÇİMLERİ (FLAG & LOG SİSTEMİ) ---

        // Seçenek 1: Savunma (Flag: Jude_Denial)
        s6_FinchOffice.addChoice(new Choice(
            "[THE DEFENSE] \"It’s a disease, not a choice.\"",
            s7_PhantomCall,
            p -> p.getSanity() > 50, "Requires Sanity > 50",
            p -> {
                // System.out.println YERİNE p.log KULLANIYORUZ
                p.log("FINCH: \"A convenient cage. If you're sick, you're not responsible.\"");
                p.updateStats(10, -15);
                p.setFlag("Jude_Denial", true); // İnkar bayrağı dikildi
            }
        ));

        // Seçenek 2: İtiraf (Flag: Jude_Guilt)
        s6_FinchOffice.addChoice(new Choice(
            "[THE ADMISSION] \"I wanted the silence.\"",
            s7_PhantomCall,
            p -> p.getConscience() >= 30, "Requires Conscience >= 30",
            p -> {
                p.log("FINCH: \"Honesty. The rarest material in architecture.\"");
                p.updateStats(-30, 40); // Reality Crash
                p.setFlag("Jude_Guilt", true); // Suçluluk bayrağı dikildi
            }
        ));

        // Seçenek 3: Saldırı (Flag: Jude_Denial)
        s6_FinchOffice.addChoice(new Choice(
            "[THE ATTACK] \"What’s your 'Ma,' Professor?\"",
            s7_PhantomCall,
            p -> p.getSanity() < 40, "Requires Low Sanity (<40)",
            p -> {
                p.log("FINCH: \"Touché. Two architects of ghosts.\"");
                p.updateStats(5, -10);
                p.setFlag("Jude_Denial", true);
            }
        ));


        // --- ACT 2: PHANTOM CALL (TELEFON) SEÇİMLERİ ---

        // 1. Pişmanlık (Sadece Jude_Guilt varsa açılır)
        s7_PhantomCall.addChoice(new Choice(
            "[REPENT] Whisper: \"I'm sorry, Jude...\"",
            null, // Demo Sonu (Null GameController tarafından yakalanır)
            p -> p.hasFlag("Jude_Guilt"), "Requires Admission of Guilt",
            p -> {
                p.log(">> The breathing pauses. A moment of peace.");
                p.updateStats(0, 5);
            }
        ));

        // 2. Engelleme (Sadece Jude_Denial varsa açılır)
        s7_PhantomCall.addChoice(new Choice(
            "[BLOCK] Block the number. It's just a prank.",
            null, // Demo Sonu
            p -> p.hasFlag("Jude_Denial"), "Requires Denial State",
            p -> {
                p.log(">> NARRATOR: Good. Don't let them scare you.");
                p.updateStats(0, -10);
            }
        ));

        // 3. Telefonu Fırlat (Herkes Yapabilir)
        s7_PhantomCall.addChoice(new Choice(
            "[HANG UP] Throw the phone across the room.",
            null, // Demo Sonu
            p -> {
                p.log(">> The phone hits the wall. Silence returns.");
                p.updateStats(-5, 0);
            }
        ));

        return s1_Laundry;
    }
}