from django.contrib.auth import get_user_model
from django.contrib.auth.password_validation import CommonPasswordValidator
from djoser.serializers import UserCreateSerializer
from django.utils.translation import gettext_lazy as _

User = get_user_model()


class MusicianUserCreateSerializer(UserCreateSerializer):
    class Meta(UserCreateSerializer.Meta):
        model = User
        fields = ('id', 'email', 'username', 'password')


