package com.laundy.laundrybackend.config.cache;

import org.apache.commons.lang3.ObjectUtils;
import org.ehcache.PersistentCacheManager;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.config.units.EntryUnit;
import org.ehcache.config.units.MemoryUnit;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.util.List;

@Configuration
public class EhCacheConfig {

    @Bean
    public PersistentCacheManager cacheManager(){
        return CacheManagerBuilder.newCacheManagerBuilder()
                .with(CacheManagerBuilder.persistence("G:\\Personal" + File.separator + "myData"))
                .withCache("services",
                        CacheConfigurationBuilder.newCacheConfigurationBuilder(String.class,List.class,
                                ResourcePoolsBuilder.newResourcePoolsBuilder()
                                        .heap(1, EntryUnit.ENTRIES)
                                        .offheap(0, MemoryUnit.MB)
                                        .disk(20, MemoryUnit.MB)
                        )
                ).build(true);
    }
}
