import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args){

        Region region = new Region("Kanto", "Warm", 1);
        Pokemon[] pokedex = new Pokemon[100];
        int pokedexCount = 0;

        try{
            File file = new File("pokedex.txt");
            Scanner fileScanner = new Scanner(file);
            while(fileScanner.hasNext() && pokedexCount < 100){
                String name = fileScanner.next();
                String type = fileScanner.next();
                int health = fileScanner.nextInt();
                int attack = fileScanner.nextInt();
                int level = 1;
                pokedex[pokedexCount] = new Pokemon(name, type, level, health, attack);
                pokedexCount += 1;
            }
            fileScanner.close();
        }catch(Exception e){
            System.out.println("pokedex.txt not found:" + e.getMessage());
            return;
        }
        Scanner input = new Scanner(System.in);
        boolean running = true;
        while(running){
            printMenu();
            System.out.print("Select Option: ");
            String option = input.nextLine();

            if (option.equals("1")) {
                System.out.println("Region Name: " + region.getName());
                System.out.println("Climate: " + region.getClimate());
                System.out.println("Difficulty: " + region.getDifficulty());
                System.out.println("**************************");
                System.out.print("Would you like to modify region name, climate, difficulty or continue? ");
                String choice = input.nextLine();
                if (choice.equals("name")) {
                    System.out.print("New Region Name: ");
                    region.setName(input.nextLine());
                } else if (choice.equals("climate")) {
                    System.out.print("New Climate: ");
                    region.setClimate(input.nextLine());
                } else if (choice.equals("difficulty")) {
                    System.out.print("New Difficulty (1-5): ");
                    String diff = input.nextLine();
                    // the user can only type 1-5
                    if(diff.equals("1") || diff.equals("2") || diff.equals("3") || diff.equals("4") || diff.equals("5")){
                        region.setDifficulty(Integer.valueOf(diff));
                    } else {
                        System.out.println("Invalid difficulty. Keeping previous value.");
                    }
                } else if(choice.equals("continue")){
                    // will make go back to menu
                } else{
                    System.out.println("Invalid option.");
                }
                System.out.println("**************************\n");
            }else if(option.equals("2")){
                System.out.print("Would you like to add or remove a trainer to/from the region? ");
                String choice = input.nextLine();
                if(choice.equalsIgnoreCase("add")){
                    System.out.print("Trainer Name: ");
                    String name = input.nextLine();
                    Trainer t = new Trainer(name, 1);
                    boolean added = region.addTrainer(t);
                    if(added){
                        System.out.println("Trainer successfully added!");
                    }else{
                        System.out.println("Trainer already exists or no space.");
                    }
                }else if(choice.equalsIgnoreCase("remove")){
                    System.out.print("Trainer Name: ");
                    String name = input.nextLine();
                    boolean removed = region.removeTrainer(name);
                    if(removed){
                        System.out.println("Trainer successfully removed!");
                    }else{
                        System.out.println("Trainer not found.");
                    }
                }else{
                    System.out.println("Invalid option.");
                }
                System.out.println("**************************\n");
            }else if(option.equals("3")){
                System.out.print("Would you like to add or remove wild Pokémon to/from the region? ");
                String choice = input.nextLine();
                if (choice.equalsIgnoreCase("add")){
                    if(pokedexCount == 0){
                        System.out.println("No Pokémon in the pokedex to add.");
                    }else{
                        int randomIdx = 0;
                        boolean found = false;
                        while(!found && randomIdx < pokedexCount){
                            if(Math.random() < 0.1){
                                found = true;
                            }else{
                                randomIdx++;
                            }
                        }
                        if(!found){
                            randomIdx = 0;
                        }
                        Pokemon poke = new Pokemon(pokedex[randomIdx].getName(), pokedex[randomIdx].getType(), pokedex[randomIdx].getLevel(), pokedex[randomIdx].getHealth(), pokedex[randomIdx].getDamage());
                        region.addWildPokemon(poke);
                        System.out.println("There's a new wild Pokemon called " + poke.getName() + " in the region!");
                    }
                }else if(choice.equalsIgnoreCase("remove")){
                    System.out.print("Wild Pokémon: ");
                    String name = input.nextLine();
                    boolean removed = region.removeWildPokemon(name);
                    if(removed){
                        System.out.println(name + " has fled the region.");
                    }else{
                        System.out.println("Wild Pokémon not found.");
                    }
                }else{
                    System.out.println("Invalid option.");
                }
                System.out.println("**************************\n");
            }else if(option.equals("4")){
                System.out.print("Which Trainer? ");
                String name = input.nextLine();
                Trainer trainer = null;
                Trainer[] trainers = region.getTrainerInRegion();
                boolean found = false;
                for(int i = 0; i < trainers.length && !found; i++) {
                    if(trainers[i] != null && trainers[i].getName().equals(name)) {
                        trainer = trainers[i];
                        found = true;
                    }
                }
                if(trainer == null){
                    System.out.println("Trainer not found.");
                }else{
                    boolean done = false;
                    while(!done){
                        System.out.println("Name: " + trainer.getName());
                        System.out.println("Is Champion? " + trainer.isPokemonChampion());
                        if(trainer.getPartner() != null){
                            System.out.println("Partner: " + trainer.getPartner().getName());
                        }else{
                            System.out.println("Partner: None");
                        }
                        System.out.print("Would you like to modify name, champ status, partner or continue? ");
                        String choice = input.nextLine();
                        if(choice.equals("name")){
                            System.out.print("New Trainer Name: ");
                            trainer.setName(input.nextLine());
                        }else if(choice.equals("champ status")){
                            System.out.print("Is Champion? (true/false): ");
                            String champ = input.nextLine();
                            trainer.setPokemonChampion(champ.equals("true"));
                        }else if(choice.equals("partner")){
                            System.out.print("New Partner Name: ");
                            String partnerName = input.nextLine();
                            trainer.choosePartner(partnerName);
                        }else if(choice.equals("continue")){
                            done = true;
                        }else{
                            System.out.println("Invalid option.");
                            done = true;
                        }
                    }
                }
                System.out.println("**************************\n");
            }else if(option.equals("5")){
                System.out.print("Which trainer? ");
                String name = input.nextLine();
                Trainer trainer = null;
                Trainer[] trainers = region.getTrainerInRegion();
                boolean trainerFound = false;
                for(int i = 0; i < trainers.length && !trainerFound; i++){
                    if(trainers[i] != null && trainers[i].getName().equals(name)){
                        trainer = trainers[i];
                        trainerFound = true;
                    }
                }
                if(trainer == null){
                    System.out.println("Trainer not found.");
                }else{
                    System.out.print("Would you like to add or remove Pokémon for Trainer? ");
                    String choice = input.nextLine();
                    if(choice.equalsIgnoreCase("add")){
                        Pokemon[] wilds = region.getWildPokemon();
                        int wildCount = 0;
                        for(int i = 0; i < wilds.length; i++) {
                            if(wilds[i] != null) {
                                wildCount++;
                            }
                        }
                        if (wildCount == 0) {
                            System.out.println("No wild Pokémon available.");
                        }else{
                            int found = -1;
                            int i = 0;
                            boolean foundFlag = false;
                            while(i < wilds.length && !foundFlag){
                                if(wilds[i] != null){
                                    found = i;
                                    foundFlag = true;
                                }
                                i += 1;
                            }
                            if(found != -1){
                                Pokemon wild = wilds[found];
                                System.out.println("Pokemon Encountered:");
                                System.out.println("Name: " + wild.getName());
                                System.out.println("Type: " + wild.getType());
                                System.out.println("Level: " + wild.getLevel());
                                System.out.println("Health: " + wild.getHealth());
                                System.out.println("Attack Damage: " + wild.getDamage());
                                trainer.addPokemon(wild);
                                region.removeWildPokemon(wild.getName());
                                System.out.println("There's a new team addition for " + trainer.getName() + "!");
                            }
                        }
                    }else if(choice.equalsIgnoreCase("remove")){
                        System.out.print("Pokemon to remove: ");
                        String pokeName = input.nextLine();
                        boolean removed = trainer.removePokemon(pokeName);
                        if(removed){
                            System.out.println(pokeName + " removed from team.");
                        }else{
                            System.out.println("Pokemon not found in team.");
                        }
                    }else{
                        System.out.println("Invalid option.");
                    }
                }
                System.out.println("**************************\n");
            }else if(option.equals("6")){
                System.out.print("Which trainer? ");
                String name = input.nextLine();
                Trainer trainer = null;
                Trainer[] trainers = region.getTrainerInRegion();
                boolean found = false;
                for(int i = 0; i < trainers.length && !found; i++) {
                    if(trainers[i] != null && trainers[i].getName().equals(name)) {
                        trainer = trainers[i];
                        found = true;
                    }
                }
                if(trainer == null){
                    System.out.println("Trainer not found.");
                }else{
                    if(trainer.getTeamCount() == 0){
                        System.out.println(trainer.getName() + " has no Pokémon in their team.");
                    }else{
                        System.out.println(trainer.getName() + "'s Pokémon Team:");
                        Pokemon[] team = trainer.getTeam();
                        int i = 0;
                        while(i < trainer.getTeamCount()){
                            Pokemon p = team[i];
                            System.out.println((i+1) + ". " + p.getName() + ", Type: " + p.getType() + ", Level: " + p.getLevel() + ", Health: " + p.getHealth());
                            i += 1;
                        }
                    }
                }
                System.out.println("**************************\n");
            }else if(option.equals("7")){
                System.out.println("Simulate interaction between two Trainers");
                System.out.print("First Trainer: ");
                String first = input.nextLine();
                System.out.print("Second Trainer: ");
                String second = input.nextLine();
                Trainer t1 = null;
                Trainer t2 = null;
                Trainer[] trainers = region.getTrainerInRegion();
                boolean found1 = false;
                boolean found2 = false;
                for(int i = 0; i < trainers.length && (!found1 || !found2); i++){
                    if(trainers[i] != null) {
                        if(!found1 && trainers[i].getName().equals(first)){
                            t1 = trainers[i];
                            found1 = true;
                        }
                        if(!found2 && trainers[i].getName().equals(second)){
                            t2 = trainers[i];
                            found2 = true;
                        }
                    }
                }
                if(t1 == null || t2 == null){
                    System.out.println("One or both trainers not found.");
                }else if(t1.getPartner() == null || t2.getPartner() == null){
                    System.out.println("One or both trainers do not have a partner Pokémon.");
                }else{
                    System.out.println("Simulating interaction between " + t1.getName() + " and " + t2.getName() + "...");
                    region.simulateInteraction(t1.getName(), t2.getName());
                }
                System.out.println("**************************\n");
            }else if(option.equals("8")){
                running = false;
                System.out.println("Exiting program. Goodbye!");
            }else{
                System.out.println("Invalid option. Please enter a number from 1 to 8.");
            }
        }
        input.close();
    }
    public static void printMenu(){
        System.out.println("******************************");
        System.out.println("* Welcome to Poke Miner *");
        System.out.println("******************************");
        System.out.println("Options");
        System.out.println("1. Modify Region");
        System.out.println("2. Add/Remove Trainer to Region");
        System.out.println("3. Add/Remove Wild Pokemon to Region");
        System.out.println("4. Modify Trainer");
        System.out.println("5. Add/remove Pokemon From Trainer");
        System.out.println("6. List Pokemon in Trainer");
        System.out.println("7. Simulate interaction between two trainers");
        System.out.println("8. Exit");
        System.out.println("***************************");
    }
}

