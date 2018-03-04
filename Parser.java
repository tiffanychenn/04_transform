import java.util.*;
import java.io.*;
import java.lang.*;

public class Parser {

    public static ArrayList<String> parse(){
        try {
            File f = new File("script");
            Scanner sc = new Scanner(f);
            ArrayList<String> stuff = new ArrayList<String>();
            while (sc.hasNext()){
                stuff.add(sc.nextLine());
            }
            sc.close();
            //System.out.println(stuff);
            return stuff;
        }
        catch (Exception e) {
            System.out.println(e);
            return new ArrayList<String>();
        }
    }

    public static void execute(Matrix edge, Matrix trans, ArrayList<String> commands, Image i){
        int n = 0;
        while (n < commands.size()){
            String c = commands.get(n);
            System.out.println(c);
            //System.out.println(trans);
            if (c.equals("line")){
                n ++;
                String[] args = commands.get(n).split(" ");
                double[] point0 = new double[] {Double.parseDouble(args[0]), Double.parseDouble(args[1]), Double.parseDouble(args[2]), 1};
                double[] point1 = new double[] {Double.parseDouble(args[3]), Double.parseDouble(args[4]), Double.parseDouble(args[5]), 1};
                edge.addEdge(point0, point1);
            }
            else if (c.equals("ident")){
                trans.ident();
            }
            else if (c.equals("scale")){
                n ++;
                String[] args = commands.get(n).split(" ");
                Matrix scale = new Matrix(4, 4);
                scale.addEdge(new double[] {Double.parseDouble(args[0]), 0, 0, 0}, new double[] {0, Double.parseDouble(args[1]), 0, 0});
                scale.addEdge(new double[] {0, 0, Double.parseDouble(args[2]), 0}, new double[] {0, 0, 0, 1});
                trans = Matrix.multi(scale, trans);
                //System.out.println(trans);
            }
            else if (c.equals("move")){
                n ++;
                String[] args = commands.get(n).split(" ");
                Matrix scale = new Matrix(4, 4);
                scale.addEdge(new double[] {1, 0, 0, 0}, new double[] {0, 1, 0, 0});
                scale.addEdge(new double[] {0, 0, 1, 0}, new double[] {Double.parseDouble(args[0]), Double.parseDouble(args[1]), Double.parseDouble(args[2]), 1});
                trans = Matrix.multi(scale, trans);
                //System.out.println(trans);
            }
            else if (c.equals("rotate")){
                n ++;
                String[] args = commands.get(n).split(" ");
                Matrix scale = new Matrix(4, 4);
                double theta = Math.toRadians(Double.parseDouble(args[1]));
                double sin = Math.sin(theta);
                double cos = Math.cos(theta);
                if (args[0].equals("x")){
                    scale.addEdge(new double[] {1, 0, 0, 0}, new double[] {0, cos, sin, 0});
                    scale.addEdge(new double[] {0, -1 * sin, cos, 0}, new double[] {0, 0, 0, 1});
                }
                else if (args[0].equals("y")){
                    scale.addEdge(new double[] {cos, 0, -1 * sin, 0}, new double[] {0, 1, 0, 0});
                    scale.addEdge(new double[] {sin, 0, cos, 0}, new double[] {0, 0, 0, 1});
                }
                else {
                    scale.addEdge(new double[] {cos, sin, 0, 0}, new double[] {-1 * sin, cos, 0, 0});
                    scale.addEdge(new double[] {0, 0, 1, 0}, new double[] {0, 0, 0, 1});
                }
                trans = Matrix.multi(scale, trans);
                //System.out.println(trans);
            }
            else if (c.equals("apply")){
            	System.out.println(edge);
            	System.out.println(trans);
                edge = Matrix.multi(trans, edge);
                //System.out.println(edge);
            }
            else if (c.equals("display")){
            	i = new Image();
                Random rand = new Random();
                int r = rand.nextInt(255);
                int g = rand.nextInt(255);
                int b = rand.nextInt(255);
                Drawing.drawlines(edge, i, new int[] {r,g,b});
                i.draw();
                Runtime run = Runtime.getRuntime();
                try {
                    run.exec("display image.ppm");
                    Thread.sleep(1000);
                }
                catch (Exception e){
                    System.out.println(e);
                }
            }
            //else if (c.equals(""))
            n ++;
        }
    }

}