const express = require('express');
const bodyParser = require('body-parser');

// create express app
const app = express();

// parse requests of content-type - application/x-www-form-urlencoded
app.use(bodyParser.urlencoded({ extended: true }));

// parse requests of content-type - application/json
app.use(bodyParser.json());

// Configure database
const dbConfig = require('./config/database.config.js');
const mongoose = require('mongoose');

mongoose.Promise = global.Promise;

try {
    mongoose.connect(dbConfig.url);
    console.log("Connection successful");
}
catch (err) {
    console.log("Error attempting to connect to database");
    process.exit();
}

require('./routes/activitat.js')(app);

// define a simple route
app.get('/', (req, res) => {
    res.json({"message": "Benvinguts a Grandapp"});
});

// listen for requests
app.listen(3000, () => {
    console.log("Server is listening on port 3000");
});
