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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

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
    @FXML
    private Label massLabel;
    private static Player player;
    private Pellet touchedPellet;
    private Pellet touchedByEnemy;
    private static ArrayList<Pellet> allPellets = new ArrayList<Pellet>();
    private static ArrayList<Enemy> allEnemy = new ArrayList<Enemy>();
    private final int constTemps = 10;
    boolean test = false;

    private Map map = Map.getInstance();
    private Camera cam = new Camera(new Coordinate(0, 0));
    private  static int rectangleSizeMinimap = 10;

    public static Player getPlayer() {
        return player;
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

    /**
     * This method initialize most variables needed to show what's happening on the screen, like the terrain, the map,
     * the player, etc.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.terrain.prefHeightProperty().bind(conteneurGlobal.heightProperty());
        this.terrain.prefWidthProperty().bind(conteneurGlobal.widthProperty());
        this.terrain.setLayoutX(0);
        this.terrain.setLayoutY(0);
        this.terrain.setFocusTraversable(true);
        this.terrain.requestFocus();

        this.miniMap.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, CornerRadii.EMPTY, Insets.EMPTY)));

        map.getAllPellet(map.getQuadTree(), allPellets);
        for (Pellet elm : allPellets) {
            addCircle(elm.getCircle());
        }

        map.getAllEnemies(map.getQuadTree(), allEnemy);

        for(Enemy elm : allEnemy){
            addCircle(elm.getCircle());
        }

        this.player = new CreatorPlayer().create();
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

        miniMap.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, CornerRadii.EMPTY, Insets.EMPTY)));

        Circle miniPlayer = new Circle(player.getCirclesList().get(0).getRadius(), Color.RED);
        miniMap.getChildren().add(miniPlayer);

        leaderboard.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, CornerRadii.EMPTY, Insets.EMPTY)));
        leaderboard.setSpacing(10);
        leaderboard.setPadding(new Insets(10, 20, 10, 10));


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
    }

    /**
     * Changes the direction of where the player goes according to the position of the cursor
     */
    EventHandler<MouseEvent> handlerMouseMoved = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent mouseEvent) {
            player.moveTowards(mouseEvent.getX(), mouseEvent.getY(), player.calculateMaxSpeed());
        }
    };

    /**
     * When the spacebar is pressed, the player will divide itself when if it should be able to
     */
    EventHandler<KeyEvent> handlerSpace = new EventHandler<KeyEvent>() {
        @Override
        public void handle(KeyEvent keyEvent) {
            if (keyEvent.getCode() == KeyCode.SPACE) {
                player.divideItself();
                for (Circle circle : player.getCirclesList()) {
                    removeCircle(circle);
                }
                for (Circle circle : player.getCirclesList()) {
                    addCircle(circle);
                }
            }
        }
    };

    /**
     * Main game method, called at the end of the initialize() method. Holds an AnimationTimer object that keeps on
     * running its content every 33ms.
     */
    private void gameLoop() {
        AnimationTimer timer = new AnimationTimer() {
            /**
             * Handles what happens every 33ms (30FPS)
             * @param now
             */
            @Override
            public void handle(long now) {
                player.move();

                massLabel.setText("Masse : " + player.getMass());

                int enemysize = allEnemy.size();
                if (enemysize < 20) {
                    spawnEnemies();
                }
                int pelletsNB = allPellets.size();
                if (pelletsNB < 1500) {
                    spawnPellets();
                }

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
                        for (Circle circle : player.getCirclesList()) {
                            if ((circle.getRadius()*circle.getRadius())/100 >= (enemy.getCircle().getRadius()*enemy.getCircle().getRadius())/100 * 1.33) {
                                player.makeFatter(enemy,circle);
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

                if (player.circlesList.size() >= 2 && !test) {
                    test = true;
                    double timeBeforeRassembling = getTimeBeforeRassembling(player.circlesList.get(0));
                    long startTime = System.currentTimeMillis();

                    AnimationTimer timer1 = new AnimationTimer() {
                        @Override
                        public void handle(long now) {
                        if (System.currentTimeMillis() - startTime >= timeBeforeRassembling * 1000) {
                            Circle c = player.rassembling(player.circlesList);
                            terrain.getChildren().remove(c);
                            player.circlesList.remove(c);
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

    /**
     * Calculates and returns the time before one circle should reunite with the other one
     * @param dividedCircle
     * @return
     */
    public double getTimeBeforeRassembling(Circle dividedCircle) {
        return this.constTemps + dividedCircle.getRadius()/100;
    }

    /**
     * Changes the scale of the black rectangle surrounding the player in the minimap
     * @param miniPlayer
     * @param rectangle
     */
    public void updateMiniMapScale(Circle miniPlayer, Rectangle rectangle) {
      rectangle.layoutXProperty().bind(miniPlayer.centerXProperty().subtract(rectangle.getWidth() / 2));
      rectangle.layoutYProperty().bind(miniPlayer.centerYProperty().subtract(rectangle.getHeight() / 2));

      rectangle.setHeight(miniPlayer.getRadius() * rectangleSizeMinimap);
      rectangle.setWidth(miniPlayer.getRadius() * rectangleSizeMinimap);
    }

    /**
     * Creates an enemy object, adds it to the allEnemy ArrayList object and adds the circle to the terrain
     */
    public void spawnEnemies() {
        Random random = new Random();
        Enemy e = new CreatorEnemy().create(random.nextDouble(0,Map.size),random.nextDouble(0,Map.size));
        allEnemy.add(e);
        addCircle(e.getCircle());
    }

    /**
     * Creates an pellet object, adds it to the allPellets ArrayList object and adds the circle to the terrain
     */
    public void spawnPellets() {
        Random random = new Random();
        Pellet p = new CreatorPellet().create(random.nextDouble(0, Map.size), random.nextDouble(0, Map.size));
        allPellets.add(p);
        addCircle(p.getCircle());
    }
}
