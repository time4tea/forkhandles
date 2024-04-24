package dev.forkhandles.data

/**
 * Map-based implementation of the DataContainer
 */
open class MapDataContainer(input: MutableMap<String, Any?> = mutableMapOf()) :
    DataContainer<MutableMap<String, Any?>>(input, { content, it -> content.containsKey(it) },
        { content, it -> content[it] },
        { map, name, value -> map[name] = value }
    )
