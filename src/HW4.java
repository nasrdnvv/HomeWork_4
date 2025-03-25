import java.util.Random;

public class HW4 {
    public static int bossHealth = 700;
    public static int bossDamage = 50;
    public static String bossDefence;
    public static int[] heroesHealth = {280, 270, 240, 100, 350, 250, 220, 200};
    public static int[] heroesDamage = {15, 20, 10, 0, 5, 10, 0, 15};
    public static String[] heroesAttackType = {"Physical", "Magical", "Kinetic", "Medic", "Golem", "Lucky", "Witcher", "Thor"};
    public static int roundNumber;
    public static Random random = new Random();

    public static void main(String[] args) {
        showStatistics();
        while (!isGameOver()) {
            playRound();
        }
    }

    public static boolean isGameOver() {
        if (bossHealth <= 0) {
            System.out.println("Герои победили!!!");
            return true;
        }
        boolean allHeroesDead = true;
        for (int heroHealth : heroesHealth) {
            if (heroHealth  > 0) {
                allHeroesDead = false;
                break;
            }
        }
        if (allHeroesDead) {
            System.out.println("Босс победил!!!");
            return true;
        }
        return false;
    }

    public static void playRound() {
        roundNumber++;
        chooseBossDefence();
        thorStun();
        bossAttack();
        medicHeal();
        heroesAttack();
        showStatistics();
    }

    public static void chooseBossDefence() {
        int randomIndex = random.nextInt(heroesAttackType.length);
        bossDefence = heroesAttackType[randomIndex];
    }

    public static void heroesAttack() {
        for (int i = 0; i < heroesAttackType.length; i++) {
            if (heroesHealth[i] > 0 && bossHealth > 0 && heroesDamage[i] > 0) {
                int damage = heroesDamage[i];
                if (heroesAttackType[i].equals(bossDefence)) {
                    int coeff = random.nextInt(2, 10);
                    damage *= coeff;
                    System.out.println("Critical damage: " + damage);
                }
                bossHealth = Math.max(bossHealth - damage, 0);
            }
        }
    }

    public static void bossAttack() {
        if (bossHealth > 0) {
            boolean isLuckyDodged = random.nextInt(100) < 25;
            int golemDamage = bossDamage / 5;
            for (int i = 0; i < heroesHealth.length; i++) {
                if (heroesHealth[i] > 0) {
                    if (heroesAttackType[i].equals("Lucky") && isLuckyDodged) {
                        System.out.println("Lucky уклонился от удара!");
                        continue;
                    }
                    int actualDamage = bossDamage;
                    if (!heroesAttackType[i].equals("Golem")) {
                        actualDamage -= golemDamage;
                    }
                    heroesHealth[i] = Math.max(heroesHealth[i] - actualDamage, 0);
                }
            }
        }
    }

    public static void medicHeal() {
        int medicIndex = 3;
        if (heroesHealth[medicIndex] > 0) {
            for (int i = 0; i < heroesHealth.length; i++) {
                if (i != medicIndex && heroesHealth[i] > 0 && heroesHealth[i] < 100) {
                    int healAmount = random.nextInt(20, 50);
                    heroesHealth[i] += healAmount;
                    System.out.println("Medic вылечил " + heroesAttackType[i] + " на " + healAmount);
                    break;
                }
            }
        }
    }

    public static void thorStun() {
        int thorIndex = 7;
        if (heroesHealth[thorIndex] > 0 && random.nextBoolean()) {
            System.out.println("Thor оглушил босса!");
            bossHealth += bossDamage;
        }
    }

    public static void showStatistics() {
        System.out.println("ROUND " + roundNumber);
        System.out.println("Boss health: " + bossHealth);
        for (int i = 0; i < heroesHealth.length; i++) {
            System.out.println(heroesAttackType[i] + " health: " + heroesHealth[i]);
        }
        System.out.println("-----------------");
    }
}

