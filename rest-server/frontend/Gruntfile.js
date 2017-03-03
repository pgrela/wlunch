module.exports = function (grunt) {
    require('load-grunt-tasks')(grunt);

    grunt.initConfig({
        ts: {
            default: {
                src: ["src/main/typescript/**.ts", "!node_modules/**"],
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
            }
        },

        connect: {
            server: {
                options: {
                    port: 8081,
                    hostname: '*',
                    base: ["src/main/html", "target/classes/META-INF/resources/"],
                    livereload: true,
                    keepalive: true,
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
        }

    });
    // grunt.loadNpmTasks("ts");
    //grunt.loadNpmTasks("typescript");
    grunt.registerTask("default", ["ts", "copy:html"]);
};