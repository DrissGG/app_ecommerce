const express = require('express');
const mysql = require('mysql');
const bodyParser = require('body-parser');
const cors = require('cors');
const path = require('path');

const app = express();
const port = 3000;

app.use(bodyParser.json());
app.use(cors());

// la connexion à la base de données MySQL
const db = mysql.createConnection({
  host: 'localhost',
  user: 'root',
  password: '',
  database: 'ecommerce'
});

db.connect((err) => {
  if (err) throw err;
  console.log('Connected to database');
});

app.get('/products', (req, res) => {
  const query = 'SELECT id, name, price, quantity, image FROM products';
  db.query(query, (err, results) => {
    if (err) {
      console.error('Failed to fetch products:', err);
      res.status(500).json({ error: 'Failed to fetch products' });
    } else {
      results.forEach(product => {
        product.image_url = `http://192.168.1.211:3000/images/${product.image}`;
      });
      res.json(results);
    }
  });
});

// Servir des fichiers statiques depuis le dossier "images"
app.use('/images', express.static(path.join(__dirname, 'images')));

app.listen(port, () => {
  console.log(`Server running at http://localhost:${port}`);
});
