
#include <gtest/gtest.h>
//#include <native-lib.h>

using namespace std;

TEST(CreateWelcomingString, NotEmpty)
{
    EXPECT_NE(1, 0);
}

TEST(CreateWelcomingString, Validity)
{
    EXPECT_EQ("Hello from C++", "Hello from C++");
}