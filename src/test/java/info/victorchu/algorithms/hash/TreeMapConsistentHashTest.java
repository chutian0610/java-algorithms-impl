package info.victorchu.algorithms.hash;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.*;

class TreeMapConsistentHashTest {
    static List<String> servers = new ArrayList<String>();
    static TreeMapConsistentHash<String> hash=null;

    @BeforeAll
    static void initData(){
        servers.add("100");
        servers.add("200");
        servers.add("300");
        servers.add("400");
        servers.add("500");
        hash = new TreeMapConsistentHash<>(servers,new TreeMapConsistentHash.DefaultHashGenerator());
    }
    @Test
    public void query(){
        System.out.println(hash.getShardInfo("101"));
        System.out.println(hash.getShardInfo("954992792"));
        System.out.println(hash.getShardInfo("95efwafraegtrgaseygtrg4992792"));
    }
}