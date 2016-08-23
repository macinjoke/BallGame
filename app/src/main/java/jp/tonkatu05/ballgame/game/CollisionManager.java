package jp.tonkatu05.ballgame.game;

public class CollisionManager {

    private Ball[] balls;
    private Map map;

    public CollisionManager(Ball[] balls, Map map){
        this.balls = balls;
        this.map = map;
    }

    public void collision(){
        Line[] lines = map.getLines();
        for(int j=0; j < balls.length; j++) {
            for(int i=0; i < lines.length; i++){
                if ((balls[j].getPositionX() > lines[i].getX()
                        && balls[j].getPositionX() < lines[i].getX() + lines[i].getLength()
                        && balls[j].getPositionY() > lines[i].getY() - balls[j].getSize()
                        && balls[j].getPositionY() < lines[i].getY() + balls[j].getSize())
                        || getDistanceByTwoPoint(balls[j].getPositionX(), balls[j].getPositionY(),
                        lines[i].getX(), lines[i].getY()) < balls[j].getSize()
                        || getDistanceByTwoPoint(balls[j].getPositionX(), balls[j].getPositionY(),
                        lines[i].getX() + lines[i].getLength(), lines[i].getY()) < balls[j].getSize()) {
                    balls[j].setCollisionFlag(true);
                    break;
                } else {
                    balls[j].setCollisionFlag(false);
                }
            }
        }
    }

    private int getDistanceByTwoPoint(int ax, int ay, int bx, int by) {
        return (int)Math.sqrt(Math.pow(ax - bx, 2)
                            + Math.pow(ay - by, 2));
    }
}
