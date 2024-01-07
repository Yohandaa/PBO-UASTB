package com.uas;

import java.sql.*;

// Interface
public interface Penilaian {
    void lihatData() throws SQLException;

    void tambahData() throws SQLException;

    void ubahData() throws SQLException;

    void hapusData();

    void cariData() throws SQLException;
}
