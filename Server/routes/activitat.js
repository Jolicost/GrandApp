module.exports = (app) => {
    const activitat = require('../controllers/activitat.js');

    // Create a new Note
    app.post('/activitats', activitat.create);

    // Retrieve all Notes
    app.get('/activitats', activitat.findAll);

    // Retrieve a single Note with noteId
    app.get('/activitats/:activitatId', activitat.findOne);

    // Update a Note with noteId
    app.put('/activitats/:activitatId', activitat.update);

    // Delete a Note with noteId
    app.delete('/activitats/:activitatId', activitat.delete);
}