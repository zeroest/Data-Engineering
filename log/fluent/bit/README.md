
## source build and install 
[[DOCS] Fluent Bit - Build and Install](https://docs.fluentbit.io/manual/installation/sources/build-and-install)

### Requirements
- CMake >= 3.12
- Flex
- Bison >= 3
- YAML headers
- OpenSSL headers

[CentOS Reference](https://github.com/fluent/fluent-bit/blob/master/dockerfiles/Dockerfile.centos7)
[Debian Reference](https://github.com/fluent/fluent-bit/blob/master/dockerfiles/Dockerfile)

```bash
sudo yum install -y \
  git gcc make cmake flex bison 
  libyaml-devel \ # debian: libyaml-dev
  openssl-devel   # debian: libssl-dev
  
git clone https://github.com/fluent/fluent-bit

cd fluent-bit/build/

cmake ../

make

make install
```
