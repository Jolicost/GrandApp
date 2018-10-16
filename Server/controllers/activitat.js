const Activitat = require('../models/activitat.js');

// Create and Save a new Note
exports.create = (req, res) => {
	// Validate request
    if(!req.body.content) {
        return res.status(400).send({
            message: "Activity content can not be empty"
        });
    }

    // Create a Note
    const activitat = new Activitat({
        title: req.body.title || "Sense titol", 
        description: req.body.description
    });

    // Save Note in the database
    Activitat.save()
    .then(data => {
        res.send(data);
    }).catch(err => {
        res.status(500).send({
            message: err.message || "Some error occurred while creating the Note."
        });
    });
};

// Retrieve and return all notes from the database.
exports.findAll = (req, res) => {
	Activitat.find()
    .then(activitat => {
        res.send(activitat);
    }).catch(err => {
        res.status(500).send({
            message: err.message || "Some error occurred while retrieving notes."
        });
    });
};

// Find a single note with a noteId
exports.findOne = (req, res) => {

};

// Update a note identified by the noteId in the request
exports.update = (req, res) => {

};

// Delete a note with the specified noteId in the request
exports.delete = (req, res) => {

};
