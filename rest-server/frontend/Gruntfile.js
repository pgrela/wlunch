module.exports = function (grunt) {
    require('load-grunt-tasks')(grunt);

    grunt.initConfig({
        ts: {
            default: {
                src: ["src/main/typescript/**.ts"],
                out: "target/classes/META-INF/resources/webjars/wlunch-rest-server/ts.js",

                tsconfig:true

                
            }
        },
        copy: {
            html: {
                expand: true,
                src: ['*', '**'],
                cwd: "src/main/html/",
                dest: 'target/classes/META-INF/resources/webjars/wlunch-rest-server/',
                filter: 'isFile'
            },
            css: {
                expand: true,
                src: ['*', '**'],
                cwd: "src/main/css/",
                dest: 'target/classes/META-INF/resources/webjars/wlunch-rest-server/css',
                filter: 'isFile'
            }
        },

        connect: {
            server: {
                options: {
                    port: 8081,
                    hostname: '*',
                    debug: true,
                    base: ["src/main/html", "src/main", "target/classes/META-INF/resources/"],
                    livereload: 49000,
                    //keepalive: true,
                    middleware: function (connect, options, middlewares) {
                        middlewares.unshift(function (req, res, next) {
                            if (!req.url.startsWith('/webjars') ||
                                req.url.startsWith('/webjars/wlunch-rest-server')) return next();
                            res.statusCode = 302;
                            res.headers = res.headers || {};
                            res.writeHeader(302, {Location:"http://localhost:8080" + req.url});
                            res.end();
                        });

                        return middlewares;
                    }
                }
            }
        },
        watch: {
            ts: {
                options: {
                    livereload: 49000
                },
                files: ['src/main/typescript/*.ts', 'src/main/typescript/**.ts'],
                tasks: ['copy:html']
            }
        },
        concurrent:{
            dev:{
                tasks: ['connect', 'watch'],
                options: {
                    logConcurrentOutput: true
                }
            }
        }

    });
    // grunt.loadNpmTasks("ts");
    //grunt.loadNpmTasks("typescript");
    grunt.registerTask("default", ["ts", "copy:html"]);
    grunt.registerTask('lserv', [
        'connect', 'watch'
    ]);
};