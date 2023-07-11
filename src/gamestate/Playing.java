//package gamestate;
//
//import entities.Entity;
//import entities.Player;
//import main.*;
//import tile.TileManager;
//
//import java.util.ArrayList;
//
//public class Playing extends State{
//
//    public Player player;
//    public TileManager tileManager;
//    public CollisionChecker collisionChecker;
//    public AssetSetter assetSetter;
//    public UI ui;
//    public Entity[] obj;
//    public Entity[] enemy;
//    ArrayList<Entity> entities = new ArrayList<>();
//    public Sound sound;
//
//    public Playing(GamePanel gamePanel) {
//        super(gamePanel);
//        initClasses();
//    }
//
//    private void initClasses() {
//        player = new Player(gamePanel, new KeyboardInputs(gamePanel));
//        tileManager = new TileManager(gamePanel);
//        collisionChecker = new CollisionChecker(gamePanel);
//        obj = new Entity[10];
//        enemy = new Entity[10];
//        assetSetter = new AssetSetter(gamePanel);
//        ui = new UI(gamePanel);
//        sound = new Sound();
//    }
//
//    public void setUpGame() {
//        assetSetter.setObject();
//        assetSetter.setEnemy();
//        GameState.state = GameState.TITLE;
////        gameState = TITLE;
//        playMusic(5);
//    }
//
//    public void restart() {
//        player.setDefaultValues();
//        assetSetter.setObject();
//        assetSetter.setEnemy();
//    }
//
//    public void playMusic(int i) {
//        sound.setFile(i);
//        sound.play();
//        sound.loop();
//    }
//
//    public void stopMusic() {
//        sound.stop();
//    }
//
//    public void playSE(int i) {
//        sound.setFile(i);
//        sound.play();
//    }
//
//
//}
