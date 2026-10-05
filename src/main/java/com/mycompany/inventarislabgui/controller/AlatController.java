/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.inventarislabgui.controller;
import com.mycompany.inventarislabgui.model.Alat;
import com.mycompany.inventarislabgui.view.AlatView;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

/**
 *
 * @author muhammadyoga
 */
public class AlatController {
        private static final int PANJANG_MAKS_KODE = 10;
    
        private final AlatView view;
        private List<Alat> daftarAlat = List.of();

    public AlatController(AlatView view) {
        this.view = view;
        view.addTambahListener(e -> tambahAlat());
        view.addUbahListener(e -> ubahAlat());
        view.addHapusListener(e -> hapusAlat());
        view.addBersihkanListener(e -> view.kosongkanForm());
        view.addPilihBarisListener(e -> pilihBaris());
    }
    
    //Handle Baca Data
    public void muatData() {
        try {
            daftarAlat = Alat.getAll();
        } catch (SQLException ex) {
            daftarAlat = List.of();
            view.tampilkanError("Gagal memuat data alat.\n" + ex.getMessage());
        }
        view.tampilkanData(daftarAlat);
    }
    
    // Membaca dan memvalidasi isi form. Mengembalikan null jika tidak valid.
    private Alat bacaInputAlat() {
        String kode = view.getKode();
        String nama = view.getNama();
        String jumlahTeks = view.getJumlah();
        String kondisi = view.getKondisi();

        if (kode.isEmpty() || nama.isEmpty() || jumlahTeks.isEmpty()) {
            view.tampilkanPeringatan("Kode, nama, dan jumlah alat wajib diisi.");
            return null;
        }

        if (kode.length() > PANJANG_MAKS_KODE) {
            view.tampilkanPeringatan("Kode alat maksimal " + PANJANG_MAKS_KODE + " karakter.");
            return null;
        }
        
        int jumlah;
        try {
            jumlah = Integer.parseInt(jumlahTeks);
        } catch (NumberFormatException ex) {
            jumlah = -1;
        }
        if (jumlah < 0) {
            view.tampilkanPeringatan("Jumlah harus berupa angka bulat 0 atau lebih.");
            return null;
        }

        return new Alat(kode, nama, jumlah, kondisi);
    }
    
    //Handle Tambah Data
    private void tambahAlat() {
        Alat alat = bacaInputAlat();
        if (alat == null) {
            return; // input tidak valid, pesan sudah ditampilkan
        }
        try {
            alat.insert();
            view.tampilkanInfo("Data alat berhasil ditambahkan.");
            muatData();
            view.kosongkanForm();
        }catch (SQLIntegrityConstraintViolationException ex) {
            view.tampilkanPeringatan("Kode alat " + alat.getKode()
                    + " sudah terdaftar.\nGunakan kode lain.");
        } catch (SQLException ex) {
            view.tampilkanError("Gagal menyimpan data alat.\n" + ex.getMessage());
        }
    }
     
    private void pilihBaris() {
        int baris = view.getBarisTerpilih();
        if (baris == -1) {
            return;
        }
        view.isiForm(daftarAlat.get(baris));
    }
    
    //Handle Tambah Data
    private void ubahAlat() {
        if (view.getBarisTerpilih() == -1) {
            view.tampilkanPeringatan("Pilih baris alat pada tabel terlebih dahulu.");
            return;
        }
        Alat alat = bacaInputAlat();
        if (alat == null) {
            return;
        }
        try {
            alat.update();
            view.tampilkanInfo("Data alat berhasil diubah.");
            muatData();
            view.kosongkanForm();
        } catch (SQLException ex) {
            view.tampilkanError("Gagal mengubah data alat.\n" + ex.getMessage());
        }
    }
    
    //Handle Hapus Data
    private void hapusAlat() {
        int baris = view.getBarisTerpilih();
        if (baris == -1) {
            view.tampilkanPeringatan("Pilih baris alat pada tabel terlebih dahulu.");
            return;
        }
        Alat alat = daftarAlat.get(baris);
        if (!view.konfirmasi("Yakin ingin menghapus alat " + alat.getKode()
                + "\n(" + alat.getNama() + ")?")) {
            return;
        }
        try {
            Alat.delete(alat.getKode());
            view.tampilkanInfo("Data alat berhasil dihapus.");
            muatData();
            view.kosongkanForm();
        } catch (SQLException ex) {
            view.tampilkanError("Gagal menghapus data alat.\n" + ex.getMessage());
        }
    }
}
