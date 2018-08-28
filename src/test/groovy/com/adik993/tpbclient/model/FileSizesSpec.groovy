package com.adik993.tpbclient.model

import spock.lang.Specification
import spock.lang.Unroll

import static com.adik993.tpbclient.model.FileSizes.*
import static java.lang.Math.pow


class FileSizesSpec extends Specification {

    @Unroll
    def "from - returns valid enum for abbreviation: #abbreviation"() {
        when:
        def result = from(abbreviation)

        then:
        result == expected

        where:
        abbreviation | expected
        "B"          | B
        "KB"         | KB
        "MB"         | MB
        "GB"         | GB
        "TB"         | TB
        "PB"         | PB
        "KiB"        | KiB
        "MiB"        | MiB
        "GiB"        | GiB
        "TiB"        | TiB
        "Pib"        | PiB
    }

    @Unroll
    def "from - handles any case string: #abbreviation"() {
        when:
        def result = from(abbreviation)

        then:
        result == GiB

        where:
        abbreviation | _
        "gib"        | _
        "GIB"        | _
    }

    @Unroll
    def "toBytes - return valid value in bytes for: #fileSize"() {
        when:
        def result = fileSize.toBytes(1)

        then:
        result == expected

        where:
        fileSize | expected
        B        | 1L
        KB       | pow(10, 3) as long
        MB       | pow(10, 6) as long
        GB       | pow(10, 9) as long
        TB       | pow(10, 12) as long
        PB       | pow(10, 15) as long
        KiB      | pow(2, 10) as long
        MiB      | pow(2, 20) as long
        GiB      | pow(2, 30) as long
        TiB      | pow(2, 40) as long
        PiB      | pow(2, 50) as long
    }

}