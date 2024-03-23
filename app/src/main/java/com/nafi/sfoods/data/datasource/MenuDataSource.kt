package com.nafi.sfoods.data.datasource

import com.nafi.sfoods.data.model.Menu

interface MenuDataSource {
    fun getMenuData(): List<Menu>
}

class MenuDataSourceImpl() : MenuDataSource {
    override fun getMenuData(): List<Menu> {
        return mutableListOf(
            Menu(
                img = "",
                name = "Nasi Goreng",
                rating = 4.9,
                price = 15000.0,
                description = "Nasi goreng, atau yang secara harfiah berarti 'nasi yang digoreng' dalam bahasa Indonesia, adalah salah satu hidangan yang paling dicintai di Indonesia. Terlepas dari sederhananya, nasi goreng adalah contoh sempurna dari kekayaan rasa dan tekstur. Nasi digoreng dalam kecap manis, bawang putih, bawang merah, cabai, dan beberapa rempah lainnya, menciptakan aroma yang memikat dan cita rasa yang kaya. Biasanya disajikan dengan irisan mentimun segar, tomat, dan kerupuk, nasi goreng merupakan hidangan yang memuaskan dan mendamaikan, cocok untuk disantap kapan pun.",
                location = "VW53+RH4, Jl. Rawa Sengon X, RT.3/RW.022, Klp. Gading Bar., Kec. Klp. Gading, Jkt Utara, Daerah Khusus Ibukota Jakarta 14240",
                mapUrl = "https://www.google.com/maps/place/Warung+Mama+Putri/@-6.2017199,106.8355434,12z/data=!4m10!1m2!2m1!1sWarung+Mama+Putri+Jakarta!3m6!1s0x2e69f551065dad77:0x80cc9c58ef0835cd!8m2!3d-6.1404828!4d106.9039447!15sChlXYXJ1bmcgTWFtYSBQdXRyaSBKYWthcnRhWhsiGXdhcnVuZyBtYW1hIHB1dHJpIGpha2FydGGSAQpyZXN0YXVyYW504AEA!16s%2Fg%2F11fyzcspqc?entry=ttu"
            ),
            Menu(
                img = "",
                name = "Mie Goreng",
                rating = 4.3,
                price = 10000.0,
                description = "Mie goreng adalah hidangan klasik Indonesia yang tak pernah lekang oleh waktu. Mie yang dimasak sempurna disajikan dalam saus kecap pedas yang disiram dengan telur, sayuran segar, potongan daging ayam, atau udang yang lezat. Rasa gurih dari kecap, kehangatan dari cabai, dan aroma harum dari bawang merah dan bawang putih membentuk harmoni sempurna di setiap gigitannya. Mie goreng bukan hanya sekadar makanan, tetapi juga merupakan bagian dari kehidupan sehari-hari masyarakat Indonesia.",
                location = "Jl. Beringin Raya No.66, RT.004/RW.003, Nusa Jaya, Kec. Karawaci, Kota Tangerang, Banten 15116",
                mapUrl = "https://www.google.com/maps/place/Bakmi+Jogja+%22KERATON%22/@-6.2018448,106.4625097,12z/data=!4m10!1m2!2m1!1swarung+mie+goreng+yogyakarta!3m6!1s0x2e69ff3282b8a1d3:0xc2121d8c9dcec670!8m2!3d-6.2018448!4d106.614945!15sChx3YXJ1bmcgbWllIGdvcmVuZyB5b2d5YWthcnRhWh4iHHdhcnVuZyBtaWUgZ29yZW5nIHlvZ3lha2FydGGSAQtub29kbGVfc2hvcJoBJENoZERTVWhOTUc5blMwVkpRMEZuU1VOd05WQm1hMnRSUlJBQuABAA!16s%2Fg%2F11hzxsc_2g?entry=ttu"
            ),
            Menu(
                img = "",
                name = "Es Coklat",
                rating = 4.7,
                price = 5000.0,
                description = "Es coklat adalah minuman yang begitu menyegarkan dan menyenangkan, terutama dalam cuaca yang panas tropis. Minuman ini terbuat dari bubuk coklat berkualitas tinggi yang dicampur dengan susu kental manis dan es serut, menciptakan paduan sempurna antara manisnya susu dan kekentalan coklat yang kaya. Tidak hanya memberikan kesegaran, tetapi juga memberikan sensasi kenikmatan yang luar biasa setiap kali diseruput.",
                location = "Jl. Titimplik No.27-33, Sadang Serang, Kecamatan Coblong, Kota Bandung, Jawa Barat 40133",
                mapUrl = "https://www.google.com/maps/place/Kedai+Es+Krim/@-6.9178269,107.5967721,13z/data=!4m10!1m2!2m1!1sKedai+Es+Krim+Bu+Tini+Bandung!3m6!1s0x2e68e652bb99164d:0x6b30a5ef3b2e8eda!8m2!3d-6.8961358!4d107.6205768!15sCh1LZWRhaSBFcyBLcmltIEJ1IFRpbmkgQmFuZHVuZ1ofIh1rZWRhaSBlcyBrcmltIGJ1IHRpbmkgYmFuZHVuZ5IBDmljZV9jcmVhbV9zaG9wmgEkQ2hkRFNVaE5NRzluUzBWSlEwRm5TVVI0WjNGNVpUTjNSUkFC4AEA!16s%2Fg%2F11bwqfwz1n?entry=ttu"
            ),
            Menu(
                img = "",
                name = "Burger",
                rating = 4.9,
                price = 20000.0,
                description = "Siapa yang bisa menolak sepotong burger yang sempurna? Burger klasik ini memiliki segalanya: patty daging sapi yang juicy, keju meleleh yang melimpah, selada segar, tomat merah matang, irisan bawang bombay, acar yang segar, dan saus istimewa yang memberikan sentuhan khas. Semua disajikan di antara roti biji wijen yang lembut dan kenyal. Setiap gigitannya membawa kombinasi cita rasa yang sempurna, membuatnya menjadi hidangan yang diinginkan oleh banyak orang di seluruh dunia.",
                location = "Loop Graha Famili, Container, Jl. Mayjend. Jonosewojo, Pradahkalikendal, Dukuhpakis, Surabaya, East Java 60227",
                mapUrl = "https://www.google.com/maps/place/Five+Monkeys+Burger+-+Surabaya+Barat/@-7.2935592,112.6002004,13z/data=!4m10!1m2!2m1!1sBurger+%26+Grill+Shack+Surabaya!3m6!1s0x2dd7fdcb083c31bd:0xf38c3f1226f4a3a4!8m2!3d-7.2935592!4d112.6764181!15sCh1CdXJnZXIgJiBHcmlsbCBTaGFjayBTdXJhYmF5YVofIh1idXJnZXIgJiBncmlsbCBzaGFjayBzdXJhYmF5YZIBFGhhbWJ1cmdlcl9yZXN0YXVyYW504AEA!16s%2Fg%2F11pf8hl9v1?entry=ttu"
            ),
            Menu(
                img = "",
                name = "Mie Rebus",
                rating = 4.4,
                price = 12000.0,
                description = "Mie rebus adalah hidangan yang memberikan kenyamanan dan kehangatan pada setiap suapan. Mie telur yang lembut direbus dalam kuah kaldu yang kaya rasa, yang terbuat dari rebusan daging sapi atau ayam yang melimpah. Ditambah dengan taburan taoge yang segar, bawang goreng yang renyah, dan irisan daun bawang yang harum, setiap suapan membawa kenikmatan yang memanjakan lidah dan jiwa.",
                location = "827Q+WH2, Rejanegara, Gumilir, Kec. Cilacap Utara, Kabupaten Cilacap, Jawa Tengah 53231",
                mapUrl = "https://www.google.com/maps/place/Warung+Kelontong+Bu+Tati/@-7.3052505,108.5250385,9z/data=!4m10!1m2!2m1!1sWarung+Makan+Bu+Tati+Bandung!3m6!1s0x2e656d2f49a3501b:0xaa3bd00883cc00c5!8m2!3d-7.6852263!4d109.0388965!15sChxXYXJ1bmcgTWFrYW4gQnUgVGF0aSBCYW5kdW5nWh4iHHdhcnVuZyBtYWthbiBidSB0YXRpIGJhbmR1bmeSARNqYXZhbmVzZV9yZXN0YXVyYW504AEA!16s%2Fg%2F11f4qcx112?entry=ttu"
            ),
            Menu(
                img = "",
                name = "Ayam Geprek",
                rating = 4.9,
                price = 8000.0,
                description = "Ayam geprek adalah perpaduan sempurna antara rasa gurih, pedas, dan renyah. Potongan ayam yang digoreng renyah dicampur dengan sambal pedas yang membara, menciptakan kombinasi cita rasa yang meledak di lidah. Setiap gigitan memberikan sensasi kenikmatan yang tak tertandingi, membuatnya menjadi hidangan favorit di banyak tempat makan di seluruh Indonesia.",
                location = "Kp. Gendong Utara No.1060, Sarirejo, Kec. Semarang Tim., Kota Semarang, Jawa Tengah 50124",
                mapUrl = "https://www.google.com/maps/place/warung+ayam+gepreK+budhe/@-7.0042336,110.425817,14z/data=!4m10!1m2!2m1!1sAyam+Geprek+Bude+Semarang!3m6!1s0x2e708d886729bbef:0x4f444c9c960bbf93!8m2!3d-6.9829343!4d110.4339548!15sChlBeWFtIEdlcHJlayBCdWRlIFNlbWFyYW5nWhsiGWF5YW0gZ2VwcmVrIGJ1ZGUgc2VtYXJhbmeSAQ9uYXNpX3Jlc3RhdXJhbnTgAQA!16s%2Fg%2F11tww48t9v?entry=ttu"
            ),
            Menu(
                img = "",
                name = "Sushi",
                rating = 4.7,
                price = 40000.0,
                description = " Sushi adalah simbol dari keahlian dan keindahan dalam dunia kuliner Jepang. Setiap gigitan dari gulungan nasi yang lembut dan segar, yang dipadukan dengan bahan laut pilihan seperti salmon, tuna, atau kepiting, memberikan pengalaman yang tak terlupakan. Disajikan dengan saus kedelai, wasabi yang pedas, dan jahe merah muda yang segar, setiap suapan sushi adalah perayaan rasa dan estetika.",
                location = "Jl. Palem Raya No.24, RT.5/RW.8, Petukangan Utara, Kec. Pesanggrahan, Kota Jakarta Selatan, Daerah Khusus Ibukota Jakarta 12260",
                mapUrl = "https://www.google.com/maps/place/Sakura+Bento+and+Sushi/@-6.2276656,106.6050276,12z/data=!4m10!1m2!2m1!1sSushi+Bar+Sakura+Jakarta!3m6!1s0x2e69f114ae13f2f5:0x865530e21fcbc78!8m2!3d-6.2276656!4d106.7574629!15sChhTdXNoaSBCYXIgU2FrdXJhIEpha2FydGFaGiIYc3VzaGkgYmFyIHNha3VyYSBqYWthcnRhkgETamFwYW5lc2VfcmVzdGF1cmFudOABAA!16s%2Fg%2F11qg15rp2s?entry=ttu"
            ),
            Menu(
                img = "",
                name = "Doritos",
                rating = 4.5,
                price = 4000.0,
                description = "Doritos adalah camilan yang sempurna untuk dinikmati di setiap kesempatan. Keripik jagung yang renyah dilapisi dengan bumbu keju pedas yang khas, menciptakan ledakan rasa di setiap gigitannya. Baik dimakan langsung atau dicelupkan ke dalam salsa segar atau saus keju, Doritos selalu menjadi pilihan yang tepat untuk menyenangkan lidah Anda.",
                location = "Jl. Tabah Raya A No.13 2, RT.2/RW.9, Klp. Gading Bar., Kec. Klp. Gading, Jkt Utara, Daerah Khusus Ibukota Jakarta 14240",
                mapUrl = "https://www.google.com/maps/place/Mini+Market+Pipal/@-6.2131585,106.8106851,12z/data=!4m10!1m2!2m1!1sMini+Mart+Jakarta!3m6!1s0x2e69f515cbd5c0b5:0xec1ef84fb8b34b4f!8m2!3d-6.1581587!4d106.886041!15sChFNaW5pIE1hcnQgSmFrYXJ0YVoTIhFtaW5pIG1hcnQgamFrYXJ0YZIBDWdyb2Nlcnlfc3RvcmXgAQA!16s%2Fg%2F11df4wc_q1?entry=ttu"
            )
        )

    }

}