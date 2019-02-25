# Task:

Should be improved to reduce calculation time.
 
1. Change file Test.java or create new one using parallel calculation mode.
2. Do not use `executors`, only plain threads  (max threads count which should be created for calculations is com.fitechsource.test.TestConsts#MAX_THREADS)
3. Try to provide simple solution, do not implement frameworks.
4. Don't forget that calculation method can throw exception, process it right way.
(Stop calculation process and print error message. Ignore already calculated intermediate results, user doesn't need it.)
