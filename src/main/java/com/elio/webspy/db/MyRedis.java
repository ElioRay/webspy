package com.elio.webspy.db;

import java.util.List;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import com.elio.webspy.Logger;
import com.elio.webspy.domain.IP;
import com.elio.webspy.util.SerializeUtil;

import redis.clients.jedis.Jedis;

public class MyRedis {
	private final Jedis jedis = RedisDB.getJedis();
	
	private static ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
	
	
	//把ip对象的信息序列化保存到jedis数据库当中
	public void saveIP(IP ip) {
		readWriteLock.writeLock().lock();	
		jedis.rpush("ip-proxy-pool".getBytes(), SerializeUtil.serialize(ip));
		readWriteLock.writeLock().unlock();
	}
	
	public void saveIPList(List<IP> ipList){
		Logger.debug("save IP");
		for(IP ip : ipList) {
			saveIP(ip);
		}
	}
	
	public IP getIP() {
		IP ip = (IP) SerializeUtil.unserialize(jedis.lpop("ip-proxy-pool".getBytes()));
		return ip;
	}
}
