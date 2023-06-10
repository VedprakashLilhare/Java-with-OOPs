import java.util.*;
public class hashmap {
    public static void main(String args[]){
        HashMap<String, Integer> map = new HashMap<>();
        map.put("india", 120);
        map.put("US", 30);
        map.put("russia", 150);
        System.out.println(map);

        int arr[] = {12,15,18};
for(int val:arr){
    System.out.println(val + " ");
    }

 for(Map.Entry<String, Integer> e:map.entrySet()){
    System.out.println(e.getKey());
    System.out.println(e.getValue());
 }

 Set<String> keys = map.keySet();
 for(String key : keys){
    System.out.println(key+ "" + map.get(key));
 }
 //map.remove("keys")
}
}
