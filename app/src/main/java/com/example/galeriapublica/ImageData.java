package com.example.galeriapublica;

import android.graphics.Bitmap;
import android.net.Uri;

import java.util.Date;

public class ImageData {
    public Uri uri;//guarda o endereco uri do arquivo de foto
    public Bitmap thumb;//guarda a  imagem em minitura da foto
    public String filename;//guarda o nome do arquivo de foto
    public Date date;//guarda a data em que a foto foi criada
    public int size;//guarda o  tamanho em bytes do arquivo de foto

    public ImageData(Uri uri, Bitmap thumb, String fileName, Date date, int size) {
        this.uri = uri;
        this.thumb = thumb;
        this.filename = fileName;
        this.date = date;
        this.size = size;
    }

}

