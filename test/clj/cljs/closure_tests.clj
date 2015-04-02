(ns cljs.closure-tests
  (:refer-clojure :exclude [compile])
  (:use cljs.closure)
  (:use clojure.test))

(deftest test-make-preamble
  (testing "no pre option"
    (is (= "" (make-preamble {}))))
  (testing "nodejs"
    (testing "with default hashbang"
      (is (= "#!/usr/bin/env node\n" (make-preamble {:target :nodejs}))))
    (testing "with custom hashbang"
      (is (= "#!/bin/env node\n" (make-preamble {:target :nodejs
                                                 :hashbang "/bin/env node"}))))
    (testing "with no hashbang"
      (is (= "" (make-preamble {:target :nodejs
                                :hashbang false})))
      (testing "and preamble"
        (is (= "var preamble1 = require(\"preamble1\");\n"
              (make-preamble {:target :nodejs
                              :hashbang false
                              :preamble ["cljs/preamble1.js"]})))))
    (testing "with preamble"
      (is (= "#!/usr/bin/env node\nvar preamble1 = require(\"preamble1\");\n"
             (make-preamble {:target :nodejs
                             :preamble ["cljs/preamble1.js"]})))))
  (testing "preamble"
    (is (= "var preamble1 = require(\"preamble1\");\nvar preamble2 = require(\"preamble2\");\n"
           (make-preamble {:preamble ["cljs/preamble1.js"
                                      "cljs/preamble2.js"]})))))

(deftest test-make-postamble
  (testing "no post option"
    (is (= "" (make-postamble {}))))
  (testing "postamble"
    (is (= "var postamble1 = require(\"postamble1\");\nvar postamble2 = require(\"postamble2\");\n"
           (make-postamble {:postamble ["cljs/postamble1.js"
                                        "cljs/postamble2.js"]})))))
