module.exports = function(grunt) {

    grunt.initConfig({
        pkg: grunt.file.readJSON('package.json'),
        useminPrepare: {
            html: 'src/index.html',
            options: {
                src: 'src',
                dest: 'app'
            }
        },
        copy: {
            dist: {
                files: [
                    {src: 'src/index.html', dest: 'app/index.html' },
                    {src: 'components/bootstrap/fonts/**', dest: 'app/fonts/', flatten:true, expand:true}
                ]
            }
        },
        usemin: {
            html: ['app/index.html'],
            css: ['app/css/style.css']
        },
        clean: ['.tmp']
    });

    grunt.loadNpmTasks('grunt-contrib-clean');
    grunt.loadNpmTasks('grunt-contrib-concat');
    grunt.loadNpmTasks('grunt-contrib-copy');
    grunt.loadNpmTasks('grunt-contrib-cssmin');
    grunt.loadNpmTasks('grunt-contrib-uglify');
    grunt.loadNpmTasks('grunt-usemin');

    grunt.registerTask('default', ['useminPrepare', 'concat', 'copy', 'uglify', 'cssmin', 'usemin', 'clean']);
};
