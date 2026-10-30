import java.util.Iterator;
import java.util.Scanner;


//objetivo é chamar a pesquisa e imprimir o numero de movimentos da solução, caso exista, ou "no solution found" caso não exista

public class Main {
    void main() throws Exception {
        Scanner sc = new Scanner(System.in); // abre a leitura do input
        int height = sc.nextInt(); //le a altura (nº de linhas = capacidade de cada tubo)
        int tubes = sc.nextInt(); //le o número de tubos
        sc.nextLine(); // descarta o resto da linha, sem isto, iria imprimir uma linha a mais no final do input
        StringBuilder configuration = new StringBuilder(); //acumula o input para passar para a pesquisa
        for (int row = 0; row < height; row++) {
            String line = sc.nextLine();
            if (line.split(" ", -1).length != tubes)//verifica se o input tem o número correto de tubos
                throw new IllegalArgumentException(); // lança exceção caso o input seja inválido 
            configuration.append(line).append('\n'); //acumula o input para passar para a pesquisa
        }

        // se quisermos imprimir apenas os filhoss
        //BallSortLayout inicial= new BallSortLayout(configuration.toString());
        //for(Ilayout f: inicial.children()) System.out.println(f);


        BestFirst s = new BestFirst(); // cria o algoritmo de pesquisa
        Iterator<BestFirst.State> it = 
                s.solve(new BallSortLayout(configuration.toString()), null);
        if (it == null) IO.println("no solution found");
        else while (it.hasNext()) {
            BestFirst.State i = it.next();
            if (!it.hasNext()) IO.println((int) i.getG());
        }
        sc.close();
    }
}
