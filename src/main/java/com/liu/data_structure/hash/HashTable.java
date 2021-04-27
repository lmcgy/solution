package com.liu.data_structure.hash;

/**
 *
 * 散列表
 * @param <K>
 * @param <V>
 */
public class HashTable<K,V> {

    private static final int DEFAULT_INIT_CAPACITY = 8;

    private static final float LOAD_FACTORY=0.75f;

    private Entity<K,V>[] table;

    private int size=0;

    /**
     * 索引（数组使用的大小）
     */
    private int use=0;


    public HashTable() {
        table = (Entity<K,V>[])new Entity[DEFAULT_INIT_CAPACITY];
    }

    /**
     * 追加
     * @param key
     * @param value
     */
    public void put(K key,V value){
        int index = hash(key);
        if (table[index]==null){
            table[index] = new Entity<>();
        }

        Entity<K,V> e = table[index];

        //利用哨兵，头节点是不进行数据的存储的
        if (e.next==null){
            e.next = new Entity<>(key,value,null);
            size++;
            use++;
            //大于阀值进行扩容
            if (use >= table.length*LOAD_FACTORY){
                resize();
            }
        }else{
            do {
                e = e.next;
                if (e.key==key ){
                    e.value = value;
                    return;
                }
            }while (e.next!=null);
            //头插
            Entity<K,V> temp = table[index].next;
            table[index].next = new Entity<>(key,value,temp);
        }
    }

    /**
     * 获取
     * @param key
     * @return
     */
    public V get(K key){
        int index = hash(key);
        Entity<K,V> e =  table[index];
        if (e == null || e.next == null) return  null;
        do {
            e= e.next;
            if (e.key == key){
                return e.value;
            }
        }while (e.next!=null);
        return null;
    }

    /**
     * 移除
     * @param key
     */
    public void remove(K key){
        int index = hash(key);
        Entity<K,V> e =  table[index];
        if (e == null || e.next == null ) return;

        Entity<K,V> pre;
        do {
            pre = e;
            e = e.next;
            if (e.key == key){
                pre.next = e.next;
                size--;
                if (table[index].next == null) use--;
                break;
            }
        }while (e.next!=null);
    }

    private void resize(){
        Entity<K,V>[] oldTab = table;
        table = (Entity<K,V>[])new Entity[table.length*2];
        use = 0;
        for (int i = 0; i < oldTab.length; i++) {
            Entity<K,V> e = oldTab[i];
            if (e==null || e.next == null) continue;

            do {
                e = e.next;
                int index = hash(e.key);
                if (table[index] == null){
                    table[index] = new Entity();
                    use++;
                }

                table[index].next = new Entity<>(e.key,e.value,table[index].next);

            }while (e.next!=null);
        }
    }


    static final int hash(Object key) {
        int h;
        return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
    }

    private static class Entity<K,V>{
        K key;
        V value;
        Entity<K,V> next;

        public Entity(K key,V value,Entity<K,V> next){
            this.key = key;
            this.value = value;
            this.next =next;
        }
        public Entity(){

        }

    }

    public static void main(String[] args) {
        HashTable table = new HashTable();
        table.put(1,"java");
        table.put(2,"web");
        table.put(3,"tomcat");
        table.put(4,"tomcat");
        table.put(5,"tomcat");
        table.put(6,"tomcat");
        table.put(7,"tomcat");
        table.put(8,"tomcat1");

        System.out.println(table.get(8));
    }

}
