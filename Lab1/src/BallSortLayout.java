import java.util.ArrayList;
import java.util.List;

class BallSortLayout implements Ilayout { 
    private final int height;
    private final int tubes;
    private final char[][] tube;
    private final int[] size;


    public BallSortLayout(String str) {
        String[] lines = str.split("\n");
        height = lines.length;
        tubes = lines[0].split(" ").length;
        tube = new char[tubes][height];
        size = new int[tubes];
        for (int row = 0; row < height; row++) {
            String[] cells = lines[row].split(" ");
            for (int t = 0; t < tubes; t++) {
                char c = cells[t].charAt(0);
                if (c != 'E') {
                    int pos = height - 1 - row;      // linha 0 e o topo
                    tube[t][pos] = c;
                    size[t]++;
                }
            }
        }
    }

    //se eu quiser imprimir um tudo em especifico, crio novo metodo
    //public String TubeToString(int t) {
        //StringBuilder sb= new StringBuilder();//cria um StringBuilder para acumular os caracteres do tubo
        //for (int p=0; p<size [t];p++) sb.append(tube[t][p]); //percorre o tubo e adiciona os caracteres ao StringBuilder
        //return sb.toString(); //retorna a string acumulada
    //}

    //indice onde está uma cor em especifico
    /**public void printIndex (char c){
     * for (int t=0; t<tubes; t++)
     *   for (int p=0; p<size(t); p++)
     *       if (tube(t)[p]==c) System.out.println("ball "+c+" is in tube "+t+" at position "+p);
    } */


    private BallSortLayout(BallSortLayout o) {
        height = o.height;
        tubes = o.tubes;
        size = o.size.clone();
        tube = new char[tubes][];
        for (int t = 0; t < tubes; t++) tube[t] = o.tube[t].clone();
    }

    @Override
    public List<Ilayout> children() {
        List<Ilayout> sucs = new ArrayList<>();
        for (int from = 0; from < tubes; from++) {
            if (size[from] == 0) continue;
            char ball = tube[from][size[from] - 1];
            for (int to = 0; to < tubes; to++) {
                if (to == from || size[to] == height) continue;
                if (size[to] > 0 && tube[to][size[to] - 1] != ball) continue;
                BallSortLayout child = new BallSortLayout(this);
                child.size[from]--;
                child.tube[from][child.size[from]] = 0;
                child.tube[to][child.size[to]] = ball;
                child.size[to]++;
                sucs.add(child);
            }
        }
        return sucs;
    }


    @Override
    public boolean isGoal(Ilayout l) {
        for (int t = 0; t < tubes; t++) {
            if (size[t] == 0) continue;
            if (size[t] != height) return false;
            for (int p = 1; p < height; p++)
                if (tube[t][p] != tube[t][0]) return false;
        }
        return true;
    }

    @Override
    public double getK() {
        return 1.0;
    }

    public double getH() {
        return 0.0;
    }
    


    // inverter  a ordem dos tubos
    /* public String tString(){
    StringBuilder sb= new StringBuilder();
    for (int t= tubes -1; t>=0; t--){
        if (t>tubes-1) sb.append(' ');
        for (int p=0; p<size[t]; p++) sb.append(tube[t][p]);
    }
    return sb.toString();
} */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int row = 0; row < height; row++) {
            int pos = height - 1 - row;
            for (int t = 0; t < tubes; t++) {
                if (t > 0) sb.append(' ');
                sb.append(pos < size[t] ? tube[t][pos] : 'E');
            }
            sb.append('\n');
        }
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BallSortLayout that = (BallSortLayout) o;
        if (height != that.height || tubes != that.tubes) return false;
        for (int t = 0; t < tubes; t++) {
            if (size[t] != that.size[t]) return false;
            for (int p = 0; p < size[t]; p++)
                if (tube[t][p] != that.tube[t][p]) return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int h = 1;
        for (int t = 0; t < tubes; t++) {
            h = 31 * h + size[t];
            for (int p = 0; p < size[t]; p++) h = 31 * h + tube[t][p];
        }
        return h;
    }
}