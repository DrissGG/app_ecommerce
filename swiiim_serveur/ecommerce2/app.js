const express = require('express');
const mysql = require('mysql');
const bodyParser = require('body-parser');
const cors = require('cors');

const app = express();
const port = 3306;

app.use(bodyParser.json());
app.use(cors());

// la connexion à la base de données MySQL
const db = mysql.createConnection({
  host: 'localhost',
  user: 'root', 
  password: '', 
  database: 'ecommerce' 
});

db.connect(err => {
  if (err) {
    console.error('Erreur de connexion à la base de données:', err);
    return;
  }
  console.log('Connecté à la base de données MySQL.');
});

// Route pour récupérer tous les produits
app.get('/products', (req, res) => {
  const sql = 'SELECT * FROM products';
  db.query(sql, (err, results) => {
    if (err) {
      console.error('Erreur lors de la récupération des produits:', err);
      res.status(500).send('Erreur serveur');
      return;
    }
    res.json(results);
  });
});

// Route pour mettre à jour la quantité d'un produit
app.put('/products/:id', (req, res) => {
  const productId = req.params.id;
  const newQuantity = req.body.quantity;
  const sql = 'UPDATE products SET quantity = ? WHERE id = ?';
  db.query(sql, [newQuantity, productId], (err, results) => {
    if (err) {
      console.error('Erreur lors de la mise à jour du produit:', err);
      res.status(500).send('Erreur serveur');
      return;
    }
    res.send('Quantité mise à jour');
  });
});

// Démarrage du serveur
app.listen(port, () => {
  console.log(`Serveur en cours d'exécution sur le port ${port}`);
});
