package com.uas;


import java.util.InputMismatchException;
import java.util.Scanner;

import java.sql.*;

// Class (Inheritance dan Interface)
public class Mahasiswa extends Tampilan implements Penilaian {
    static Connection conn;
    // Pengolahan Database (CRUD)
    String url = "jdbc:mysql://localhost/uas_pbo";

    public int nimMahasiswa = 0;
    public int nilaiTugas = 0;
    public int nilaiUTS = 0;
    public int nilaiUAS = 0;
    public double nilaiAkhir = 0;
    public char gradeNilai;
    public String namaMahasiswa;
    Scanner terimaInput = new Scanner(System.in);

    public void lihatData() throws SQLException {
        String text1 = "\n===Daftar Seluruh Data Mahasiswa===";
        // Method String
        System.out.println(text1.toUpperCase());
        // Inheritance (class Tampilan)
        tampilData();
    }

    public void tambahData() throws SQLException {
        String text2 = "\n===Tambah Data Mahasiswa===";
        // Method String
        System.out.println(text2.toUpperCase());

        try {
            // NIM Mahasiswa
            System.out.print("Masukkan NIM mahasiswa: ");
            nimMahasiswa = terimaInput.nextInt();
            terimaInput.nextLine();

            // Nama Mahasiswa
            System.out.print("Masukkan nama mahasiswa: ");
            namaMahasiswa = terimaInput.nextLine();

            System.out.print("\nNama dan NIM mahasiswa: ");
            System.out.println(namaMahasiswa + " (" + nimMahasiswa + ")");

            // Nilai Tugas
            System.out.print("\nMasukkan nilai tugas: ");
            nilaiTugas = terimaInput.nextInt();
            terimaInput.nextLine();

            // Nilai UTS
            System.out.print("Masukkan nilai UTS: ");
            nilaiUTS = terimaInput.nextInt();
            terimaInput.nextLine();

            // Nilai UAS
            System.out.print("Masukkan nilai UAS: ");
            nilaiUAS = terimaInput.nextInt();
            terimaInput.nextLine();

            // Nilai Akhir (Proses Matematika)
            nilaiAkhir = (nilaiTugas * 0.2 + nilaiUTS * 0.4 + nilaiUAS * 0.4);
            nilaiAkhir = (int) nilaiAkhir;
            System.out.println("\nNilai Akhir: " + nilaiAkhir);

            // Grade
            if (nilaiAkhir >= 85 && nilaiAkhir <= 100) {
                gradeNilai = 'A';
            } else if (nilaiAkhir >= 75 && nilaiAkhir <= 84) {
                gradeNilai = 'B';
            } else if (nilaiAkhir >= 65 && nilaiAkhir <= 74) {
                gradeNilai = 'C';
            } else if (nilaiAkhir >= 40 && nilaiAkhir <= 64) {
                gradeNilai = 'D';
            } else if (nilaiAkhir >= 0 && nilaiAkhir <= 39) {
                gradeNilai = 'E';
            }
            System.out.println("Grade: " + gradeNilai);

            // Pengolahan Database (CRUD)
            String sql = "INSERT INTO nilai_mahasiswa_si (nim_mahasiswa, nama_mahasiswa, nilai_tugas, nilai_uts, nilai_uas, nilai_akhir, grade) VALUES ('"
                    + nimMahasiswa + "','" + namaMahasiswa + "','" + nilaiTugas + "','" + nilaiUTS + "','" + nilaiUAS
                    + "','" + nilaiAkhir + "','" + gradeNilai + "')";
            conn = DriverManager.getConnection(url, "root", "");
            Statement statement = conn.createStatement();
            statement.execute(sql);
            System.out.println("Berhasil input data");
        } catch (SQLException e) {
            System.err.println("Terjadi kesalahan input data");
        } catch (InputMismatchException e) {
            System.err.println("Inputlah dengan angka saja");
        }
    }

    public void ubahData() throws SQLException {
        String text3 = "\n===Ubah Data Mahasiswa===";
        // Method String
        System.out.println(text3.toUpperCase());

        try {
            lihatData();
            System.out.print("\nMasukkan NIM mahasiswa yang akan di ubah atau update : ");
            Integer nimMahasiswa = Integer.parseInt(terimaInput.nextLine());

            // Pengolahan Database (CRUD)
            String sql = "SELECT * FROM nilai_mahasiswa_si WHERE nim_mahasiswa = " + nimMahasiswa;
            conn = DriverManager.getConnection(url, "root", "");
            Statement statement = conn.createStatement();
            ResultSet result = statement.executeQuery(sql);

            if (result.next()) {

                System.out.print("Nama baru [" + result.getString("nama_mahasiswa") + "]\t: ");
                String namaMahasiswa = terimaInput.nextLine();

                // Pengolahan Database (CRUD)
                sql = "UPDATE nilai_mahasiswa_si SET nama_mahasiswa='" + namaMahasiswa + "' WHERE nim_mahasiswa='"
                        + nimMahasiswa + "'";

                if (statement.executeUpdate(sql) > 0) {
                    System.out.println("Berhasil memperbaharui data mahasiswa (NIM: " + nimMahasiswa + ")");
                }
            }
            statement.close();
        } catch (SQLException e) {
            System.err.println("Terjadi kesalahan dalam mengedit data");
            System.err.println(e.getMessage());
        }
    }

    public void hapusData() {
        String text4 = "\n===Hapus Data Mahasiswa===";
        // Method String
        System.out.println(text4.toUpperCase());

        try {
            lihatData();
            System.out.print("\nKetik NIM Mahasiswa yang akan dihapus : ");
            Integer nimMahasiswa = Integer.parseInt(terimaInput.nextLine());

            // Pengolahan Database (CRUD)
            String sql = "DELETE FROM nilai_mahasiswa_si WHERE nim_mahasiswa = " + nimMahasiswa;
            conn = DriverManager.getConnection(url, "root", "");
            Statement statement = conn.createStatement();

            if (statement.executeUpdate(sql) > 0) {
                System.out.println("Berhasil menghapus data mahasiswa (NIM: " + nimMahasiswa + ")");
            }
        } catch (SQLException e) {
            System.out.println("Terjadi kesalahan dalam menghapus data");
        }
    }

    public void cariData() throws SQLException {
        String text5 = "\n===Cari Data Mahasiswa===";
        // Method String
        System.out.println(text5.toUpperCase());

        // Inheritance (class Tampilan)
        tampilCariData();
    }
}
