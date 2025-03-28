package com.example.sae4_project.Controller;

import com.example.sae4_project.Entity.*;
import com.example.sae4_project.QuadTree.Camera;
import com.example.sae4_project.QuadTree.Coordinate;
import com.example.sae4_project.QuadTree.Map;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.Scene;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.*;

public class AgarioController extends Controller {
    @FXML
    private Pane terrain;
    @FXML
    private Pane miniMap;
    @FXML
    private VBox leaderboard;
    @FXML
    private AnchorPane conteneurGlobal;




    private ArrayList<Circle> listCirclesPlayer = new ArrayList<Circle>();
    private static Player player;
    private Pellet touchedPellet;
    private Pellet touchedByEnemy;
    private static ArrayList<Pellet> allPellets = new ArrayList<Pellet>();
    private static ArrayList<Enemy> allEnemy = new ArrayList<Enemy>();
    private double posX;
    private double posY;
    private final int constTemps = 10;
    private long t = -1;
    private boolean isTaskCompleted = false;
    private double cptFrames = 0.0;
    boolean test = false;

    private Map map = Map.getInstance();
    private Camera cam = new Camera(new Coordinate(0, 0));
    private  static int rectangleSizeMinimap = 10;

    public static Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
        //System.out.println("setter player Agario : " + player.getName());
    }


    public static List<Pellet> getPellets() {
        return allPellets;
    }

    @FXML
    private void addCircle(Circle circle) {
        this.terrain.getChildren().add(circle);
    }

    @FXML
    private void removeCircle(Circle circle) {
        this.terrain.getChildren().remove(circle);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("AgarioController: " + player.getName());
        this.terrain.prefHeightProperty().bind(conteneurGlobal.heightProperty());
        this.terrain.prefWidthProperty().bind(conteneurGlobal.widthProperty());
        this.terrain.setLayoutX(0);
        this.terrain.setLayoutY(0);
        this.terrain.setFocusTraversable(true);
        this.terrain.requestFocus();



        map.getAllPellet(map.getQuadTree(), allPellets);
        for (Pellet elm : allPellets) {
            addCircle(elm.getCircle());
        }

        map.getAllEnemies(map.getQuadTree(), allEnemy);

        for(Enemy elm : allEnemy){
            addCircle(elm.getCircle());
        }


        Circle circle = player.getCirclesList().get(0);
       addCircle(circle);

        cam.getCoordinate().XProperty().bind( Bindings.add(Bindings.multiply(-1,
                Bindings.divide( conteneurGlobal.widthProperty(), 2)) , circle.centerXProperty()));
        cam.getCoordinate().YProperty().bind(Bindings.add(Bindings.multiply(-1,
                Bindings.divide( conteneurGlobal.heightProperty(), 2)), circle.centerYProperty()));
        cam.zoomProperty().bind(Bindings.divide(7,
                Bindings.createDoubleBinding(()-> Math.sqrt(Math.sqrt(Math.sqrt(6*player.massProperty().get()))), player.massProperty())));

        terrain.translateXProperty().bind(Bindings.multiply(-1,cam.getCoordinate().XProperty()));
        terrain.translateYProperty().bind(Bindings.multiply(-1,cam.getCoordinate().YProperty()));

        terrain.translateXProperty().bind(Bindings.multiply(terrain.scaleXProperty(), Bindings.multiply(-1,cam.getCoordinate().XProperty())));
        terrain.translateYProperty().bind(Bindings.multiply(terrain.scaleYProperty(),Bindings.multiply(-1,cam.getCoordinate().YProperty())));

        terrain.scaleXProperty().bind(cam.zoomProperty());
        terrain.scaleYProperty().bind(cam.zoomProperty());

        this.terrain.addEventHandler(MouseEvent.MOUSE_MOVED, handlerMouseMoved);
        this.terrain.addEventHandler(KeyEvent.KEY_PRESSED, handlerSpace);

        // Setup of the minimap
        miniMap.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, CornerRadii.EMPTY, Insets.EMPTY)));

        // Creation of the player on the minimap
        Circle miniPlayer = new Circle(player.getCirclesList().get(0).getRadius(), Color.RED);
        miniMap.getChildren().add(miniPlayer);

        //Listen to the player to update the minimap
        for (Circle circle1 : player.getCirclesList()) {
            circle1.centerXProperty().addListener((obs, oldVal, newVal) -> updateMiniMapScale(miniPlayer));
            circle1.centerYProperty().addListener((obs, oldVal, newVal) -> updateMiniMapScale(miniPlayer));
        }


        displayNamePlayer();
        
        
        leaderboard.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, CornerRadii.EMPTY, Insets.EMPTY)));
        leaderboard.setSpacing(10);
        leaderboard.setPadding(new Insets(10, 20, 10, 10));  // (haut, droite, bas, gauche)


        Integer [] nombre = {1,2,3,4,5,6,7,8,9,10};
        Label [] noms = {new Label("joli"), new Label("sobriquet"), new Label("s'éclipse"), new Label("parole"),
                new Label("de"), new Label("moine"), new Label("dérolle"), new Label("dodécacophonique"),
                new Label("rythmique"), new Label("globule10")};

        for (int i = 0; i < 10 ; i++) {
            Label label = new Label(nombre[i].toString() + ". " + noms[i].getText());
            label.setPrefSize(200.0, 200.0);
            leaderboard.getChildren().add(label);
        }

        this.gameLoop();


        // Creation of the rectangle representing the visible area on the minimap
       Rectangle rectangle = new Rectangle();
        rectangle.setStroke(Color.BLACK);
        rectangle.setWidth(miniPlayer.getScaleX() * rectangleSizeMinimap);
        rectangle.setHeight(miniPlayer.getScaleY() * rectangleSizeMinimap);
        rectangle.setFill(Color.TRANSPARENT);
       rectangle.setStrokeWidth(1);
        miniMap.getChildren().add(rectangle);

        for(Circle circle1 : player.circlesList) {
            circle1.centerXProperty().addListener((obs, oldVal, newVal) -> updateMiniMapScale(miniPlayer, rectangle));
            circle1.centerYProperty().addListener((obs, oldVal, newVal) -> updateMiniMapScale(miniPlayer, rectangle));
        }


        System.out.println("AgarioController: " + player.getName());
    }

    private void displayNamePlayer() {
        Text playerNameText = new Text(player.getName());
        playerNameText.setFill(Color.AQUA);
        playerNameText.setStyle("-fx-font-weight: bold; -fx-font-size: 10px;");

        // Add the text in the scene
        terrain.getChildren().add(playerNameText);

        // Link the position of the text to that of the player's circle
        Circle playerCircle = player.getCirclesList().get(0);
        playerNameText.xProperty().bind(playerCircle.centerXProperty().add(playerCircle.radiusProperty())); //position X at top on right
        playerNameText.yProperty().bind(playerCircle.centerYProperty().subtract(playerCircle.radiusProperty())); //position Y at top on right

    }

    EventHandler<MouseEvent> handlerMouseMoved = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent mouseEvent) {
            player.moveTowards(mouseEvent.getX(), mouseEvent.getY(), player.calculateMaxSpeed());
        }
    };

    EventHandler<KeyEvent> handlerSpace = new EventHandler<KeyEvent>() {
        @Override
        public void handle(KeyEvent keyEvent) {
            if (keyEvent.getCode() == KeyCode.SPACE) {
                for (Circle circle : player.getCirclesList()) {
                    removeCircle(circle);
                }player.divideItself();

                for (Circle circle : player.getCirclesList()) {
                    addCircle(circle);
                }
            }
        }
    };

    /**
     * Update the minimap
     */
    private void updateMiniMapScale(Circle miniPlayer) {
        double scale = miniMap.getWidth() / Map.size;

        // Update the player on the minimap
        for (Circle circle : player.getCirclesList()) {
            miniPlayer.setRadius(circle.getRadius() * scale);
            miniPlayer.centerXProperty().bind(circle.centerXProperty().multiply(scale));
            miniPlayer.centerYProperty().bind(circle.centerYProperty().multiply(scale));
        }
    }


    private void gameLoop() {
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                player.move();
                Random random = new Random();
                int enemysize = allEnemy.size();
                if (enemysize < 20) {
                    spawnEnemies();
                }
                int pelletsNB = allPellets.size();
                if (pelletsNB < 1500) {
                    spawnPellets();
                }
                //.getAllEnemies(map.getQuadTree(), allEnemy);
                //QuadTree zone = map.findQuadTree(map.getQuadTree(), new Coordinate(12,12));
                //zone.getEntities().add(enemi);
                touchedPellet = player.detectPellet(allPellets);
                if (touchedPellet != null) {
                    for (Circle circle : player.getCirclesList()) {
                        if (circle.getBoundsInLocal().intersects(touchedPellet.getCircle().getBoundsInLocal())) {
                            player.makeFatter(touchedPellet, circle);
                            terrain.getChildren().remove(touchedPellet.getCircle());
                            allPellets.remove(touchedPellet);
                            break;
                        }
                    }
                }
                touchedPellet = null;

                for (int i = 0; i < allEnemy.size(); i++) {
                    Enemy enemy = allEnemy.get(i);
                    enemy.executeStrategy(now);

                    touchedByEnemy = enemy.detectPellet(allPellets);
                    if (touchedByEnemy != null) {
                        enemy.makeFatter(touchedByEnemy, enemy.circle);
                        terrain.getChildren().remove(touchedByEnemy.getCircle());
                        allPellets.remove(touchedByEnemy);
                    }

                    enemy.move();

                    for (int j = i + 1; j < allEnemy.size(); j++) {
                        Enemy otherEnemy = allEnemy.get(j);
                        if (enemy.isColliding(otherEnemy)) {
                            if (enemy.circle.getRadius() > otherEnemy.circle.getRadius()) {
                                enemy.makeFatter(otherEnemy, enemy.circle);
                                terrain.getChildren().remove(otherEnemy.getCircle());
                                allEnemy.remove(j);
                                j--;
                            } else if (enemy.circle.getRadius() < otherEnemy.circle.getRadius()) {
                                otherEnemy.makeFatter(enemy, otherEnemy.circle);
                                terrain.getChildren().remove(enemy.getCircle());
                                allEnemy.remove(i);
                                i--;
                                break;
                            }
                        }
                    }

                    if (player.isColliding(enemy)) {
                        double playerMass = player.getMass();
                        double enemyMass = enemy.getMass();

                        for (Circle circle : player.getCirclesList()) {
                            if ((circle.getRadius()*circle.getRadius())/100 >= (enemy.getCircle().getRadius()*enemy.getCircle().getRadius())/100 * 1.33) {
                                player.makeFatter(enemy,circle);
                                //player.circle.setFill(Color.BLACK);
                                terrain.getChildren().remove(enemy.getCircle());
                                allEnemy.remove(i);
                                i--;
                            } else if ((enemy.getCircle().getRadius()*enemy.getCircle().getRadius())/100 >= (circle.getRadius()*circle.getRadius())/100 * 1.33) {
                                stop();
                                Platform.runLater(() -> {
                                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                    alert.setTitle("Game Over");
                                    alert.setHeaderText("Tu t'es fait manger !");
                                    alert.setContentText("Dommage, tu as perdu le jeu.");
                                    alert.showAndWait().ifPresent(response -> {
                                        if (response == ButtonType.OK) {
                                            System.exit(0);
                                        }
                                    });
                                });
                            }
                        }
                    }
                }
                System.out.println("-0 : "+player.circlesList.toString());
                System.out.println(player.circlesList.size());
                if (player.circlesList.size() >= 2 && !test) {
                    System.out.println(" list :" +player.circlesList.toString());
                    test = true;
                    double timeBeforeRassembling = getTimeBeforeRassembling(player.circlesList.get(0));
                    long startTime = System.currentTimeMillis();

                    AnimationTimer timer1 = new AnimationTimer() {
                        @Override
                        public void handle(long now) {
                            if (System.currentTimeMillis() - startTime >= timeBeforeRassembling * 100) {
                                System.out.println("1 : "+player.circlesList.toString());
                                player.rassembling(player.circlesList.get(0),player.circlesList.get(player.circlesList.size()-1));
                                terrain.getChildren().remove(player.circlesList.get(player.circlesList.size()-1));
                                player.circlesList.remove(player.circlesList.get(player.circlesList.size()-1));
                                System.out.println("2 : "+player.circlesList.toString());
                                stop();
                                test = false;
                            }
                        }
                    };
                    timer1.start();
                }
            }
        };
        timer.start();
    }

    public double getTimeBeforeRassembling(Circle dividedCircle) {
        return this.constTemps + dividedCircle.getRadius()/100;
    }
    public void eatingAnimation(double oldMass, double newMass) {
    }

    public void updateMiniMapScale(Circle miniPlayer, Rectangle rectangle) {
    //Link the position of the rectangle with that of the miniPlayer
      rectangle.layoutXProperty().bind(miniPlayer.centerXProperty().subtract(rectangle.getWidth() / 2));
      rectangle.layoutYProperty().bind(miniPlayer.centerYProperty().subtract(rectangle.getHeight() / 2));

      //adapt the rectangle and the player
      rectangle.setHeight(miniPlayer.getRadius() * rectangleSizeMinimap);
      rectangle.setWidth(miniPlayer.getRadius() * rectangleSizeMinimap);
    }


    public void spawnEnemies() {
        Random random = new Random();
        Enemy e = new CreatorEnemy().create(random.nextDouble(0,Map.size),random.nextDouble(0,Map.size));
        allEnemy.add(e);
        addCircle(e.getCircle());
    }

    public void spawnPellets() {
        Random random = new Random();
        //Pellet p = new Pellet(random.nextDouble(0, Map.size), random.nextDouble(0, Map.size));
        Pellet p = new CreatorPellet().create(random.nextDouble(0, Map.size), random.nextDouble(0, Map.size));
        allPellets.add(p);
        addCircle(p.getCircle());
    }
}
