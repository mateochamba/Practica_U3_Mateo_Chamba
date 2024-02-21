package controlador.TDA.listas;

import controlador.TDA.listas.Exception.EmptyException;

/**
 *
 * @author FA506ICB-HN114W
 */
public class DynamicList<E> {

    private Node<E> header;
    private Node<E> last;
    private Integer lenght;

    public DynamicList() {
        header = null;
        last = null;
        lenght = 0;
    }

    public Node<E> getHeader() {
        return header;
    }

    public void setHeader(Node<E> header) {
        this.header = header;
    }

    public Node<E> getLast() {
        return last;
    }

    public void setLast(Node<E> last) {
        this.last = last;
    }

    public Integer getLenght() {
        return lenght;
    }

    public void setLenght(Integer lenght) {
        this.lenght = lenght;
    }

    public Boolean isEmpty() {
        return header == null || getLenght() == 0;
    }

    private void addFirst(E info) {
        Node<E> help;
        if (isEmpty()) {
            help = new Node<>(info);
            header = help;
            last = help;

        } else {
            Node<E> headHelp = header;
            help = new Node<>(info, headHelp);
            header = help;

        }
        lenght++;
    }
     public void modificarPosicion(E dato, Integer pos) throws Exception{
        if(isEmpty()){
            add(dato);
        }
        else if(pos>=0 && pos < lenght){
            if(pos == 0){
                header.setInfo(dato);
            } 
            else{
                Node<E> aux = header;
                for (int i = 0; i < pos; i++ ){
                    aux = aux.getNext();
                }
                aux.setInfo(dato);
            }
        }
        else{
            throw new Exception();
        }
    }

    private void addLast(E info) {
        Node<E> help;
        if (isEmpty()) {
            addFirst(info);
        } else {
            help = new Node<>(info, null);
            last.setNext(help);
            last = help;
            lenght++;
        }
    }

    public void add(E info) {
        addLast(info);
    }

    public void AgregarFinal(E info){
        Node<E> Ayuda;
        if(isEmpty()){
            addFirst(info);
        }
        else{
            Ayuda = new Node<>(info, null);
            last.setNext(Ayuda);
            last = Ayuda;
            lenght++;
        }
    }
    public void add(E info, Integer index) throws EmptyException, IndexOutOfBoundsException {
        if (index.intValue() == 0) {
            addFirst(info);
        } else if (index.intValue() == lenght.intValue()) {
            addLast(info);
        } else {
            Node<E> search_preview = getNode(index - 1);
            Node<E> search = getNode(index);
            Node<E> help = new Node<>(info, search);
            search_preview.setNext(help);
            setLenght((Integer) (getLenght() + 1));
        }
    }

    private E getFirst() throws EmptyException, IndexOutOfBoundsException {
        if (isEmpty()) {
            throw new EmptyException("Error. Lista vacia");
        }
        return header.getInfo();
    }

    public E getInfo(Integer index) throws EmptyException, IndexOutOfBoundsException {
        try {
            return getNode(index).getInfo();
        } catch (IndexOutOfBoundsException e) {
            throw new IndexOutOfBoundsException("Error. Fuera de rango. Índice: " + index);
        }
    }

    public void Limpiar() {
        header = null;
        last = null;
        lenght = 0;
    }
    public E delete(Integer pos) throws EmptyException, IndexOutOfBoundsException {
        E dato;
        if (isEmpty()) {
            throw new EmptyException("La lista está vacía");
        }
        if (pos < 0 || pos >= lenght) {
            throw new IndexOutOfBoundsException("Posición fuera de rango");
        }
        if (pos == 0) {
            dato = header.getInfo();
            header = header.getNext();
            if (lenght == 1) {
                last = null;
            }
        } 
        else {
            Node<E> aux = header;
            for (int i = 0; i < pos - 1; i++) {
                aux = aux.getNext();
            }
            dato = aux.getNext().getInfo();
            aux.setNext(aux.getNext().getNext());
            if (pos == lenght - 1) {
                last = aux;
            }
        }
        lenght--;
        return dato;
    }

    private Node<E> getNode(Integer index) throws EmptyException {
        if (isEmpty()) {
            throw new EmptyException("Error. Lista vacia");
        } else if (index < 0 || index >= lenght) {
            throw new IndexOutOfBoundsException("Error. Fuera de rango");
        } else if (index == 0) {
            return header;
        } else if (index == (lenght - 1)) {
            return last;
        } else {
            Node<E> search = header;
            Integer cont = 0;
            while (cont < index) {
                cont++;
                search = search.getNext();
            }
            return search;
        }
    }

    /*public E merge(E data, Integer index) throws EmptyException, IndexOutOfBoundsException {
        Node<E> search = getNode(index);
        E help = search.getInfo();
        search.setInfo(data);
        return help;
    }*/
    public void merge(E data, Integer index) throws EmptyException {
        if (isEmpty()) {
            throw new EmptyException("Error, la lista esta vacia");
        } else if (index < 0 || index >= lenght) {
            throw new IndexOutOfBoundsException("Error, esta fuera del limite de la lista");
        } else if (index == 0) {
            header.setInfo(data);
        } else if (index == (lenght - 1)) {
            last.setInfo(data);
        } else {
            Node<E> search = header;
            Integer cont = 0;
            while (cont < index) {
                cont++;
                search = search.getNext();
            }
            search.setInfo(data);
        }
    }

    public E extractFirst() throws EmptyException {
        if (isEmpty()) {
            throw new EmptyException("Lista vacia");
        } else {
            E element = header.getInfo();
            Node<E> help = header.getNext();
            header = null;
            header = help;
            if (lenght == 1) {
                last = null;
            }
            lenght--;
            return element;
        }
    }

    public E extractLast() throws EmptyException {
        if (isEmpty()) {
            throw new EmptyException("Lista vacia");
        } else {
            E element = last.getInfo();
            Node<E> help = getNode(lenght - 2);
            if (help == null) {
                last = null;
                if (lenght == 2) {
                    last = header;
                } else {
                    header = null;
                }
            } else {
                last = null;
                last = help;
                last.setNext(null);
            }
            lenght--;
            return element;
        }
    }

    public E extract(Integer index) throws EmptyException {
        if (isEmpty()) {
            throw new EmptyException("Error. Lista vacia");
        } else if (index.intValue() < 0 || index.intValue() >= lenght) {
            throw new IndexOutOfBoundsException("Error. Fuera de rango");
        } else if (index == 0) {
            return extractFirst();
        } else if (index == (lenght - 1)) {
            return extractLast();
        } else {
            Node<E> node_preview = getNode(index - 1);
            Node<E> node_actually = getNode(index);
            E info = node_actually.getInfo();
            Node<E> help_next = node_actually.getNext();
            node_actually = null;
            node_preview.setNext(help_next);
            lenght--;
            return info;
        }
    }

    public E[] toArray() {
        Class clazz = null;
        E[] matriz = null;
        if (lenght > 0) {
            clazz = header.getInfo().getClass();
            matriz = (E[]) java.lang.reflect.Array.newInstance(clazz, lenght);
            Node<E> aux = header;
            for (int i = 0; i < lenght; i++) {
                matriz[i] = aux.getInfo();
                aux = aux.getNext();
            }
        }
        return matriz;
    }

    public DynamicList<E> toList(E[] m) {
        reset();
        for (int i = 0; i < m.length; i++) {
            this.add(m[i]);
        }
        return this;
    }

    public void reset() {
        header = null;
        last = null;
        lenght = 0;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Lista Data\n");
        try {
            Node<E> help = header;
            while (help != null) {
                sb.append(help.getInfo()).append("\n");
                help = help.getNext();
            }
        } catch (Exception e) {
            sb.append(e.getMessage());
        }
        return sb.toString();
    }

    public E[] toArray(Class<E> clazz) {
        E[] array = (E[]) java.lang.reflect.Array.newInstance(clazz, lenght);
        Node<E> aux = header;
        for (int i = 0; i < lenght; i++) {
            array[i] = aux.getInfo();
            aux = aux.getNext();
        }
        return array;
    }

    // Nuevo método para reconstruir la lista a partir de un array
    public DynamicList<E> fromArray(E[] array) {
        reset();
        for (int i = 0; i < array.length; i++) {
            this.add(array[i]);
        }
        return this;
    }

    public void reverse() {
        Node<E> prev = null;
        Node<E> current = header;
        Node<E> next;

        while (current != null) {
            next = current.getNext();
            current.setNext(prev);
            prev = current;
            current = next;
        }

        header = prev;
    }
//    public boolean contains(int value) {
//    Node<E> current = header;
//    while (current != null) {
//        if (current.getInfo().equals(value)) {
//            return true;
//        }
//        current = current.getNext();
//    }
//    return false;
//}

    public void deleteAll() {
        this.header = null;
        this.lenght = 0;
    }

    public boolean contains(E value) {
        Node<E> current = header;
        while (current != null) {
            if (current.getInfo().equals(value)) {
                return true;
            }
            current = current.getNext();
        }
        return false;
    }

    public void actualizar(Integer indice, E nuevoValor) throws IndexOutOfBoundsException, EmptyException {
        if (indice < 0 || indice >= lenght) {
            throw new IndexOutOfBoundsException("Error. Fuera de rango");
        }

        Node<E> actual = getNode(indice);
        actual.setInfo(nuevoValor);
    }

    public void insertarPrimero(E info) {
        Node<E> help;
        if (isEmpty()) {
            help = new Node<>(info);
            header = help;
            last = help;
        } else {
            Node<E> headHelp = header;
            help = new Node<>(info, headHelp);
            header = help;
        }
        lenght++;
    }

    public void imprimir() throws Exception {
        if (isEmpty()) {
            throw new Exception();
        } else {
            Node<E> aux = header;
            System.out.println("----LISTA------");
            for (int i = 0; i < getLenght(); i++) {
                System.out.print(aux.getInfo() + "  ");
                aux = aux.getNext();
            }
            System.out.println("");
            System.out.println("----LISTA FIN------");
        }

    }

}
