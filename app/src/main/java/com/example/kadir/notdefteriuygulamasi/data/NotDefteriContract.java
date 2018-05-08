package com.example.kadir.notdefteriuygulamasi.data;

import android.provider.BaseColumns;

public class NotDefteriContract {

    public static final class NotlarEntry implements BaseColumns{

        public static final String TABLE_NAME = "notlar";

        public static final String _ID = BaseColumns._ID;
        public static final String COLUMN_NOTE_ICERIK = "notIcerik";
        public static final String COLUMN_OLUSTURMA_TARİHİ = "olusturulmaTarihi";
        public static final String COLUMN_BITIS_TARIHI = "bitisTarihi";
        public static final String COLUMN_YAPILDI = "yapildi";
        public static final String COLUMN_KATEGORI_ID = "kategoriId";

    }

    public static final class KategoriEntry implements BaseColumns{

        public static final String TABLE_NAME = "kategoriler";

        public static final String _ID = BaseColumns._ID;
        public static final String COLUMN_KATEGORI = "kategori";
    }
}
