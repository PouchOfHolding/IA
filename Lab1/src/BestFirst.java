import java.util.*;

class BestFirst {
    protected Queue<State> abertos; // fila de prioridade com os layouts a serem testados
    private Map<Ilayout, State> fechados; // mapa com os layouts já testados, para não testar novamente
    private State actual; // estado atual, que está a ser testado
    private Ilayout objective; // layout objetivo, que é o layout que queremos alcançar

    // classe que representa um estado da pesquisa, que é um layout e o seu pai,
    //  para poder reconstruir o caminho da solução
    static class State {
        private Ilayout layout;
        private State father; // pai do estado atual, que é o estado que gerou o estado atual
        private double g;// custo do caminho até este estado, que é a soma do custo do pai com o custo do layout atual

        // verifica se tem pai, se tiver, soma o custo do layout atual com o custo do pai, se não tiver, o custo é 0
        public State(Ilayout l, State n) { // construtor da classe State, que recebe um layout e o seu pai
            layout = l;
            father = n;
            if (father != null)
                g = father.g + l.getK();
            else g = 0.0;
        }


        public String toString() { return layout.toString(); }
        public double getG() { return g; }
        public int hashCode() { return toString().hashCode(); }

        // compara se dois estados são iguais, ou seja, se os seus layouts são iguais
        public boolean equals(Object o) {
            if (o == null) return false;
            if (this.getClass() != o.getClass()) return false;
            State n = (State) o;
            return this.layout.equals(n.layout);
        }
    }
    // retorna uma lista com todos os layouts filhos do layout atual, que são os layouts que podem ser alcançados em um movimento legal
    final private List<State> sucessores(State n) {
        List<State> sucs = new ArrayList<>(); // lista com os layouts filhos do layout atual
        List<Ilayout> children = n.layout.children(); // lista com os layouts filhos do layout atual, que são os layouts que podem ser alcançados em um movimento legal
        for (Ilayout e : children) { // percorre a lista de layouts filhos do layout atual
            if (n.father == null || !e.equals(n.father.layout)) {
                State nn = new State(e, n);// cria um novo estado com o layout filho e o estado atual como pai
                sucs.add(nn);
            }
        }
        return sucs;
    }

    // método que implementa a pesquisa Best First, que recebe um layout inicial e um layout objetivo, 
    // e retorna um iterador com os estados da solução, ou null se não houver solução
    final public Iterator<State> solve(Ilayout s, Ilayout goal) {
        this.objective = goal;
        abertos = new PriorityQueue<>(10,// cria uma fila de prioridade com os layouts a serem testados, que é ordenada pelo custo do caminho até o layout atual
                (s1, s2) -> (int) Math.signum(s1.getG() - s2.getG()));// compara dois estados pelo custo do caminho até o layout atual
        fechados = new HashMap<>();// cria um mapa com os layouts já testados, para não testar novamente
        abertos.add(new State(s, null));// adiciona o layout inicial à fila de prioridade com os layouts a serem testados
        List<State> sucs;// lista com os layouts filhos do layout atual, que são os layouts que podem ser alcançados em um movimento legal
    

        while(!abertos.isEmpty()){  //até ficarmos sem opcoes para testar

            actual = abertos.poll(); //retira o layout com menor custo da fila de prioridade com os layouts a serem testados

            if( actual.layout.isGoal(objective)){ //verifica se o layout atual é o objetivo, ou seja, se o layout atual é igual ao layout objetivo

                List<State> caminho = new ArrayList<>();
                State temp = actual;
                while(temp != null){
                    caminho.add(temp);
                    temp = temp.father;
                }

                Collections.reverse(caminho);

                return caminho.iterator();
            }

            fechados.put(actual.layout, actual);
            sucs = sucessores(actual);

            for(State child : sucs){//percorre a lista de layouts filhos do layout atual

                if(!fechados.containsKey(child.layout) && !abertos.contains(child)){

                    abertos.add(child);
                }
            }
        }

        return null;
    }
}
