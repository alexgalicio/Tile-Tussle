package utilz;

public class Constants {
    public static class EnemyConstants {
        public static final int SLIME = 0;

        public static int GetSpriteAmount(int enemyType) {
            switch (enemyType) {
                case SLIME ->
                {
                    return 4;
                }
            }
            return 1;
        }
    }
    public static class ObjectConstants {
        public static final int TILES = 0;

        public static int GetSpriteAmount(int objectType) {
            switch (objectType) {
                case TILES -> {
                    return 4;
                }
            }
            return 1;
        }
    }
    public static class PlayerConstants {
        public static final int IDLE = 0;
        public static final int WALK = 1;
        public static final int DEAD = 2;

        public static int GetSpriteAmount(int playerAction) {
            switch (playerAction) {
                case IDLE, WALK -> {
                    return 4;
                }
                case DEAD -> {
                    return 5;
                }
                default -> {
                    return 1;
                }
            }
        }
    }
}
