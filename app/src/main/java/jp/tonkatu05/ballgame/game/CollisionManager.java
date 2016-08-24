package jp.tonkatu05.ballgame.game;

public class CollisionManager {

    private Ball[] mBalls;
    private Map mMap;

    public CollisionManager(Ball[] balls, Map map){
        this.mBalls = balls;
        this.mMap = map;
    }

    public void collision(){
        Line[] lines = mMap.getLines();
        for(int j = 0; j < mBalls.length; j++) {
            for(int i=0; i < lines.length; i++){
                if ((mBalls[j].getPositionX() > lines[i].getX()
                        && mBalls[j].getPositionX() < lines[i].getX() + lines[i].getLength()
                        && mBalls[j].getPositionY() > lines[i].getY() - mBalls[j].getSize()
                        && mBalls[j].getPositionY() < lines[i].getY() + mBalls[j].getSize())
                        || getDistanceByTwoPoint(mBalls[j].getPositionX(), mBalls[j].getPositionY(),
                        lines[i].getX(), lines[i].getY()) < mBalls[j].getSize()
                        || getDistanceByTwoPoint(mBalls[j].getPositionX(), mBalls[j].getPositionY(),
                        lines[i].getX() + lines[i].getLength(), lines[i].getY()) < mBalls[j].getSize()) {
                    mBalls[j].setCollisionFlag(true);
                    break;
                } else {
                    mBalls[j].setCollisionFlag(false);
                }
            }
        }
    }

    private int getDistanceByTwoPoint(int ax, int ay, int bx, int by) {
        return (int)Math.sqrt(Math.pow(ax - bx, 2)
                            + Math.pow(ay - by, 2));
    }
}
