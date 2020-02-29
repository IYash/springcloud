package com.clnn.search;

        import java.util.Scanner;

/**
 * 图实现基本模型之邻接矩阵
 */
public class Graph {

    private int vertexSize;//顶点数量
    private int[] vertex;//顶点数组
    private int[][] matrix;//邻接矩阵

    public static final int MAX=50;

    public Graph() {
    }

    public Graph(int vertexSize){
        this.vertexSize = vertexSize;
        matrix = new int[vertexSize][vertexSize];
        vertex = new int[vertexSize];
        for(int i=0;i<vertexSize;i++){
            vertex[i] = i;
        }
    }

    public static void main(String[] args) {
        Graph graph = new Graph();
        graph = graph.demoGraph();
        System.out.println("输入你想看的顶点:");
        Scanner sc =new Scanner(System.in);
        int mm=0;
        while(sc.hasNext() && mm!=-1) {
            mm=sc.nextInt();
            int num = graph.getOutDegree(mm);
            System.out.println("顶点"+mm+"的出度是:"+num);
        }
        sc.close();

    }
    public Graph demoGraph(){
        int vertexSize = 7;
        Graph graph = new Graph(vertexSize);
        //行描述的是出边的权值，列描述入边的权值，所以说浪费空间
        //-------------------1,2,3,4,5,6,7
        int[] a1 = new int[]{0,1,1,1,MAX,MAX,MAX};
        int[] a2 = new int[]{1,0,MAX,MAX,1,MAX,MAX};
        int[] a3 = new int[]{1,MAX,0,1,1,1,1};
        int[] a4 = new int[]{1,MAX,1,0,MAX,1,MAX};
        int[] a5 = new int[]{MAX,1,1,MAX,0,1,MAX};
        int[] a6 = new int[]{MAX,MAX,1,1,1,0,MAX};
        int[] a7 = new int[]{MAX,MAX,1,MAX,MAX,MAX,0};
        graph.matrix[0]=a1;
        graph.matrix[1]=a2;
        graph.matrix[2]=a3;
        graph.matrix[3]=a4;
        graph.matrix[4]=a5;
        graph.matrix[5]=a6;
        graph.matrix[6]=a7;
        return graph;
    }
    public int getOutDegree(int index){
        int degree = 0;
        for(int j=0;j<matrix[index].length;j++){
            if(matrix[index][j]>0 && matrix[index][j]<MAX)
                degree++;
        }
        return degree;
    }
    public int[] getMatrixVertex(int index){
        return matrix[index];
    }
}
