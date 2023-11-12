// OTELLER TABLOSUNDA ARAMA 
String query = "SELECT h.id, h.name, h.city, h.region, h.adress, h.email, h.phone, h.star, " +
                "GROUP_CONCAT(DISTINCT f.name ORDER BY f.name ASC SEPARATOR ', ') AS otel_ozellikleri, " +
                "GROUP_CONCAT(DISTINCT n.name ORDER BY n.name ASC SEPARATOR ', ') AS hotel_pension " +
                "FROM hotel AS h " +
                "LEFT JOIN hotel_prop_relations AS hf ON h.id = hf.hotel_id " +
                "LEFT JOIN oprop AS f ON hf.feature_id = f.id " +
                "LEFT JOIN hotel_pensions_relations AS hpn ON h.id = hpn.hotel_id " +
                "LEFT JOIN hotel_pensions AS n ON hpn.name_pensions_id = n.id " +
                "WHERE h.name LIKE ? AND h.city LIKE ? AND h.region LIKE ? " +
                "GROUP BY h.id";

//OTEL SİLME
// İlk olarak hotel_prop_relations tablosundan ilgili otel ilişkilerini siliyoruz
            String query1 = "DELETE FROM hotel_prop_relations WHERE hotel_id = ?";

// Daha sonra oteli hotel tablosundan siliyoruz
            String query = "DELETE FROM hotel WHERE id = ?";

//OTEL EKLEME
"INSERT INTO hotel (name, city, region, adress, email, phone, star) VALUES (?, ?, ?, ?, ?, ?, ?)";

//OTELİN PERİOD BİLGİLERİNİ GETİRME
= "SELECT id FROM periods WHERE startDate = ? AND finishDate = ?";

//OTELE DÖNEM EKLEME
"INSERT INTO period_hotel_relations (hotel_id, period_id) VALUES (?, ?)";

//OTEL GÜNCELLEME
"UPDATE hotel AS h LEFT JOIN hotel_prop_relations AS hf ON h.id = hf.hotel_id LEFT JOIN oprop AS f ON hf.feature_id = f.id " +
                "SET h.name = ?, h.city = ?, h.region = ?, h.adress = ?, h.email = ?, h.phone = ?, h.star = ?, h.otel_ozellikleri = GROUP_CONCAT(f.name SEPARATOR ',') " +
                "WHERE h.id = ?";

//OTEL LİSTESİNİ GETİRME
"SELECT h.id, h.name,h.adress, h.city, h.region, h.email, h.phone, h.star,\n" +
                "       GROUP_CONCAT(DISTINCT f.name SEPARATOR ', ') AS otel_ozellikleri,\n" +
                "       GROUP_CONCAT(DISTINCT n.name SEPARATOR ', ') AS hotel_pension\n" +
                "FROM hotel AS h\n" +
                "LEFT JOIN hotel_prop_relations AS hf ON h.id = hf.hotel_id\n" +
                "LEFT JOIN oprop AS f ON hf.feature_id = f.id \n" +
                "LEFT JOIN hotel_pensions_relations AS hpn ON h.id = hpn.hotel_id\n" +
                "LEFT JOIN hotel_pensions AS n ON hpn.name_pensions_id = n.id \n" +
                "GROUP BY h.id";

//İD Sİ ? OLAN OTELİ GETİRME
"SELECT * FROM hotel WHERE id = ? ";

//PERİOD SİLME (HEM PERİOD TABLOSUNDAN HEM OTEL İLE İLİŞKİ TABLOSUNDAN HEM DE ODA LİSTESİNDEN)
"DELETE FROM period_hotel_relations WHERE period_id = ?";
        String deletePeriodSql = "DELETE FROM periods WHERE id = ?";
        String deleteRoomsSql = "DELETE FROM roomlist WHERE period_start = ? AND period_finish = ?";

//İD DEN İLGİLİ PERİODU GETİRME
"SELECT * FROM periods WHERE id = ?";

//PERİOD EKLEME
"INSERT INTO periods (startDate, finishDate) VALUES (?, ?)";

//OTEL İD YE GÖRE PERİODLARI LİSTELEME
SELECT p.startDate, p.finishDate, h.name AS hotelName " +
                "FROM periods p " +
                "INNER JOIN period_hotel_relations phr ON p.id = phr.period_id " +
                "INNER JOIN hotel h ON phr.hotel_id = h.id " +
                "WHERE phr.hotel_id = ?";

//PERİODLARIN TÜMÜNÜ LİSTELEME
"SELECT * FROM periods";

//REZERVASYONLARI LİSTELEME
"SELECT r.id, r.name, r.note, r.phone, r.room_id, r.room_name, r.pension, r.hotel_name, r.start_rez, r.finish_rez, r.adult_num, r.total_adult_price, r.child_num, r.total_child_price, " +
                "rl.name AS room_name " +
                "FROM reservations r " +
                "JOIN roomlist rl ON r.room_id = rl.id";

//REZERVASYON EKLEME
"INSERT INTO reservations (name, note, phone, room_id, room_name, pension, start_rez, finish_rez, hotel_name, adult_num, total_adult_price, child_num, total_child_price)" +
                " SELECT ?, ?, ?, ? AS room_id, rl.name AS room_name, rl.pension, ?, ?, " +
                "(SELECT name FROM hotel WHERE id = rl.hotel_id) AS hotel_name, ?, ? * rl.adult_price * ? AS total_adult_price, " +
                "?, ? * rl.child_price * ? AS total_child_price FROM roomlist rl WHERE rl.id = ?";

//REZERVASYON SİLME
"DELETE FROM reservations WHERE id = ?";

//ODA ARAMA
"SELECT r.id, r.name AS room_name, r.stock, r.period_start, r.period_finish, r.pension, r.adult_price, r.child_price,h.name AS hotel_name, h.adress AS hotel_address, h.star AS hotel_star," +
                    "GROUP_CONCAT(f.name SEPARATOR ', ') AS hotel_features FROM roomlist AS r JOIN hotel AS h ON r.hotel_id = h.id LEFT JOIN hotel_prop_relations " +
                    "AS hf ON h.id = hf.hotel_id LEFT JOIN oprop AS f ON hf.feature_id = f.id WHERE h.city LIKE ? OR h.region LIKE ? OR h.name LIKE ? OR (r.period_start <= ? AND r.period_finish >= ?) " +
                    "GROUP BY r.id, r.name, r.stock, r.period_start, r.period_finish, r.pension, r.adult_price, r.child_price,h.name, h.adress, h.star";

//OTEL İD YE GÖRE ODA LİSTELE
"SELECT * FROM roomlist WHERE hotel_id = ?";

//ODA EKLE
"INSERT INTO roomlist (hotel_id, name, stock,period_start,period_finish,pension, bed_num, tv, minibar, playstation, msquare, security_case, projection,adult_price,child_price) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

//ODA SİL
"DELETE FROM roomlist WHERE id = ?";
//YETİŞKİN FİYAT GÜNCELLE
"UPDATE roomlist SET adult_price = ? WHERE id = ?";
//ÇOCUK FİYAT GÜNCELLE
"UPDATE roomlist SET child_price = ? WHERE id = ?";

//KULLANICILARI LİSTELE
"SELECT * FROM user";

//KULLANICI EKLE
"INSERT INTO user (name,uname,pass,type) VALUES (?,?,?,?)";
//KULLANICI SİL
"DELETE FROM user WHERE id = ?";

//İLGİLİ KULLANICIYI GETİR
"SELECT * FROM user WHERE uname = ?";
"SELECT * FROM user WHERE uname = ? AND pass = ?";

//KULLANICI BİLGİLERİNİ GÜNCELLE
"UPDATE user SET name=?,uname=?,pass=?,type=? WHERE id=?";

//KULLANICI ARA
"SELECT * FROM user WHERE uname LIKE '%" + uname + "%' AND name LIKE '%" + name + "%'";

//İD Sİ EN BÜYÜK İD Yİ OTELLERDEN SEÇ 
"SELECT MAX(id) FROM hotel"

//OTEL VE PERİODU EŞLE
"INSERT INTO period_hotel_relations (hotel_id, period_id) VALUES (?, ?)";

//PERİODLARI LİSTLE
"SELECT * FROM periods";

//OTEL PANSİYONLARINI EKLE
"INSERT INTO hotel_pensions_relations (hotel_id, name_pensions_id) VALUES (?, ?)";

//OTEL ÖZELLİKLERİNİ EKLE
INSERT INTO hotel_prop_relations (hotel_id, feature_id) VALUES (?, ?)";

//İLGİLİ OTEL İD DEKİ PERİODLARI LİSTELE
"SELECT p.id, p.startDate, p.finishDate " +
                "FROM periods p " +
                "INNER JOIN period_hotel_relations phr ON p.id = phr.period_id " +
                "WHERE phr.hotel_id = ?";

//İLGİLİ OTEL İD DEKİ PANSİYONLARI LİSTELE
"SELECT hr.name_pensions_id, p.name " +
                "FROM hotel_pensions_relations hr " +
                "INNER JOIN hotel_pensions p ON hr.name_pensions_id = p.id " +
                "WHERE hr.hotel_id = ?";

//SEÇİLEN OTEL PANSİYONLARINI EKLEME
INSERT INTO hotel_pensions_relations (hotel_id, name_pensions_id) VALUES (?, ?)";

//SEÇİLEN OTEL ÖZELLİKLERİNİ EKLEME
"INSERT INTO hotel_prop_relations (hotel_id, feature_id) VALUES (?, ?)";
