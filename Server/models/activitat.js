var mongoose = require('mongoose'),
    Schema   = mongoose.Schema;

var activitat = new Schema({
  title:    { type: String },
  description:  { type: String }
});

module.exports = mongoose.model('Activitat', activitat);