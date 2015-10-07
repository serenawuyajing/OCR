'use strict';

var CHANGE_LOG_PATHNAME = '../CHANGELOG.md';
var README_PATHNAME = '../README.md';

function md2html(content) {
  var lines = content.split('\n');
  for (var i = 0; i < lines.length; i++) {
    var line = lines[i];
    var prefix = 0;
    var fchar = line.charAt(0);
    if (fchar === '#') {
      // Check prefix '#' for title level.
      while (line.charAt(++prefix) === '#');
      var hsize = (prefix < 5 ? prefix : 5);
      lines[i] = '<h' + hsize + '>' + line.substring(prefix, line.length) + '</h' + hsize + '>';
    } else if (fchar === '-') {
      // Check for seperator.
      while (line.charAt(++prefix) === '-');
      if (prefix = line.length && prefix >= 3) {
        lines[i] = '<hr>'
      }
    }
  }
  return '<p>' + lines.join('</p><p>') + '</p>';
};

(function() {
  $.get(README_PATHNAME, function(content) {
    $('#readme').html(md2html(content));
  }, 'text');
  $.get(CHANGE_LOG_PATHNAME, function(content) {
    $('#changelog').html(md2html(content));
  }, 'text');
})();
