package be.alexandre01.inazuma.uhc.generations;

import be.alexandre01.inazuma.uhc.InazumaUHC;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;

import java.util.ArrayList;

public class Plateform {
    public enum PlateformType{
        CUBE,SCHEMATICA,SQUARE;
    }

    private PlateformType plateformType;
    private double x;
    private double y;
    private double z;
    private  int height;
    private int width;
    private int depth;
    private ArrayList<Block> bs;
    public static ArrayList<Plateform> registerPlateform = new ArrayList<>();
    public Plateform(PlateformType plateformType, double x, double y, double z){
        this.plateformType = plateformType;
        this.x = x;
        this.y = y;
        this.z = z;
        this.bs = new ArrayList<>();
    }

    public void setSquare(int width,int depth){
        System.out.println("setSquare");
        this.width =  width;
        this.depth = depth;
    }
    public void setCube(int height,int width, int depth){
        this.height =  height;
        this.width = width;
        this.depth = depth;
    }

    public void spawn(){
        System.out.println("spawn");
        World w = InazumaUHC.get.worldGen.defaultWorld;
        if(plateformType.equals(PlateformType.CUBE)){
            for (int i = -depth/2; i <(depth/2)+1; i++) {
                for (int j = -width/2; j < (width/2)+1; j++) {
                    System.out.println("x > "+i +" | y > "+(int) y+" | z > "+j);
                    Block b = w.getBlockAt(i, (int) y,j);
                    b.setType(Material.GLASS);
                    bs.add(b);

                    if(i == -depth/2 || i == depth/2 || j == -width/2 || j == width/2){
                        for (int k = (int) y; k < y+(height/2)+1; k++) {
                            Block b2 = w.getBlockAt(i,k,j);
                            b2.setType(Material.GLASS);
                            bs.add(b2);

                        }
                    }
                }

            }
            for (int i = -this.depth/2; i < this.depth/2; i++) {
                for (int j = -width/2; j < width/2; j++) {
                    Block b =  w.getBlockAt(i, (int) (y+height/2),j);
                   b.setType(Material.GLASS);
                   bs.add(b);
                }
            }
            System.out.println(bs.size());
            return;
        }
        if(plateformType.equals(PlateformType.SQUARE)){
            System.out.println("square spawn");
            for (int i = -width/2; i < width/2; i++) {
                System.out.println("square i> "+i);
                for (int j = -depth/2; j < depth/2; j++) {
                    System.out.println("square j> "+j);
                    System.out.println("x/i : "+i +"y :"+ (int)y +"z/j :"+ j);
                    Block b =  w.getBlockAt((int) (x+i), (int) y, (int) (z+j));
                    b.setType(Material.GLASS);
                    bs.add(b);
                }

            }
        }
        if(plateformType.equals(PlateformType.SCHEMATICA)){

        }
    }
    public void addRegisterPlateform(){
        registerPlateform.add(this);
    }
    public void remRegisterPlateform(){
        registerPlateform.remove(this);
    }
    public void despawn(){
        System.out.println("despawn> " +this.bs.size());
        for (Block b: bs){
            System.out.println("despawn> "+b);
            b.setType(Material.AIR);
        }
    }

    public int size(){
        if(width > depth){
            return width;
        }else {
            return depth;
        }
    }
}
